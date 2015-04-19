# Rendering process

Rendering an image involves several steps. The general thought process is as follows: what objects are placed on the scene? What are they made of and how does **light** interact with them? Where is the camera placed, and where is it pointing to? How many light sources are present in the scene, and which ones have an effect on which objects? What rendering options are enabled?

To answer these questions, this chapter will outline the classes representing a scene, all designed in an object-oriented style, using common design patterns when relevant.

We will then concentrate on how ray tracing --- the technique used for rendering --- works: the physics and mathematics involved, common light interactions, and CSG operations.

## Scenes

We call "scene" the composition of *elements* and *parameters* that, after the rendering process is finished, define what the final image looks like. In CRT, a scene is represented by the `Scene` class which contains all the entities that will be drawn, as well as all important information on how to draw them:

- A list of entities, the objects composing the rendered world
- A list of light sources
- A camera
- Other settings, stored in a `Settings` object

### Entities \label{subsec:entities}

Entities are **primitive volumes** that can easily be described with *mathematical equations*, such as boxes (*parallelepipeds*), spheres, cones, planes and half-planes, tori, etc. 

Every entity has a position in space and must provide an `intersect()` method to compute its eventual intersection point or points with any given ray, which we will need later on to do the rendering. 

Entities also contain a `Material` property, which defines what material the entity is made out of. Materials possess several attributes that describe how light interacts with it:

- A colour, provided by the `Pigment` class
- Reflectivity, for shiny surfaces
- Transparency, defining how many photons can go through.
- Refractive index, defining how much light is slowed down when passing through the material.
- A diffuse factor, which makes light bounce diffusely.
- Specularity, for harsh highlights (this is a computer graphics trick, it is not physically accurate).
- Shininess, defining how sharp the specular highlight will be.

Thinking about ability to compose creative scenes, one can ask: "Isn't only having *cubes and spheres* a bit limited?" To remedy this, users can compose groups of entities using the result of a *CSG*^[Constructive solid geometry] *operation*, which can be either a union, a difference or an intersection.

All of these operations will be explained in further details in section \ref{sec:raytracing} about ray tracing.

\customfig{uml/entity.eps}{The \texttt{\footnotesize Entity} class diagram}{}{entityclass}

We can notice that the `CSG` operators follow the *composite* design pattern, being an entity type composed of other entities.

### Light sources

Light sources illuminate a scene and give entities a component of their colors. When ray tracing, they are the targets of all the rays we *back-trace* from the camera lens and bounce off entities.

Several light source types exist: spotlight (cone), cylinder, parallel, and point. For now, only point light sources are implemented.

A light source is defined by the `Light` class and has the following properties:

- An origin, defining from where the light is shining.
- A *falloff* factor: describes the natural effect observable in nature, where light follows an inverse square law: the intensity of light from a point source is inversely proportional to the square of the distance from the source. We receive only a fourth of the photons from a light source twice as far away.
- A colour, given by the `Pigment` class
- An ambient light factor: because simulating global illumination is mathematically difficult and takes a lot of processing, we can simulate ambient light (accumulation of light that bounces of many surfaces) by setting an ambient factor, which will basically add a fraction of the value of its colour and intensity. 

One has to keep in mind that each additional light source adds up to the amount of rays to bounce and thus linearly increase computation time.

### Camera

A lit and populated scene still needs a window through which we will observe it: the `Camera` class defines the point of view of our rendered scene.

It has a **position** and a **direction** vector, as well as a **field of view** angle.

The **field of view** of a camera *how much* it sees from left to right, or from top to bottom of the image (in photography, this would represent the focal length of the objective). In CRT, the field of view is defined vertically, as an angle in radians. Varying this parameter has a *zoom* effect when lowered, while a big value makes more things visible on the screen.

To further add to the user's creative possibilities, several artistic features which aim to mimic real-life cameras were implemented:

- Depth of field (DOF), effect that creates a plane in which objects are sharp, and blurry outside, akin to a tilt-shift effect in photography.
- An aperture shape, which will be used to physically simulate the shape that *bokeh* will have (see figure \ref{fig:bokeh}).
- A focal distance, defining at which distance objects are sharp.

\customfig{img/bokeh.jpg}{Real-life \emph{bokeh}}{: the blurriness of out-of-focus objects will take the shape of the camera's aperture (pinhole). Here, the \emph{bokeh} is pentagonal.}{bokeh}

### Settings

The `Settings` class encapsulates all remaining options for customizing the way we render a scene:

- Picture resolution
- Gamma value
- Super-sampling factor
- Number of DOF samples
- Recursion depth

The meaning of these settings will further be explained in the section regarding ray tracing.

### Rendering process summary

In the following class diagram are all the main classes involved in the rendering of a scene. The `Tracer` class contains the static methods responsible for the actual ray tracing. They are invoked with a `Scene` object as a parameter, which contains references to all of the other classes.

\customfig{uml/rendering.eps}{Rendering process class diagram}{}{renderclass}

## Ray tracing \label{sec:raytracing}

A lot of elements were defined thus far --- but just what *is* ray tracing? First, a bit of history of computer graphics.

Traditionally, 3D computer graphics are rendered using a technique called **rasterisation**. Compared to ray tracing, rasterisation is extremely fast and is more suited for real-time applications, and takes advantage of years of hardware development dedicated to accelerating it.

In the rasterisation world, a 3D scene is described by a collection of **polygons**, usually triangles, defined by 3 three-dimensional vertices. A rasteriser will take a stream of such vertices, transform them into corresponding two-dimensional points on the viewer's monitor, and fill in the transformed two-dimensional triangles (with either lines, or colours). 

\customfigB{img/rasterisation.png}{Rasterisation of a triangle}{: once the vertices have been projected on the screen, a discrete pixel is ``lit'' if its continuous centre is contained within the projected triangle's boundaries}{}

Some effects of *light* observed in real life can be reproduce (or at least *mimicked*) on top of rasterisation. For example, if a polygon is not directly facing the camera (i.e. its *normal vector* is not parallel with the camera's direction), the resulting colour of the rasterised triangle will be darker.

However, the very nature of rasterisation makes it hard to implement other very common effects:

- To reproduce shadows, complicated stencil buffers must be used, along with a depth buffer computed by rendering a sub-scene from the point of view of the light source. This not only is complex but the results look very pixelated
- Refraction is very hard to reproduce. For a long time, raster application went without refraction effects and just have less opaque models. Nowadays, advanced pixel shaders use techniques similar to ray tracing

Ray tracing solves these issues, at the cost of being slower. 

Instead of projecting things *from* the scene on the screen like with rasterisation, ray tracing is about *sending* rays *towards* the various elements of the scene. 

But why this way? In real life, light sources send protons in all directions at random. Some of them hit objects, which *absorb* some of the energy from the photons (thus changing the perceived colour). The photons are then reflected, bouncing *off* the object with a mirrored angle of incidence^[Note that is angle is generally not exactly the mirrored incident angle and is in fact mostly random. Perfect surfaces like mirrors will indeed bounce off photons with a perfect angle (**specular** reflection), but most surfaces will scatter the protons in all directions (**diffuse** reflection --- that is why stones are not reflective like a mirror, their surface is *rough* all incoming photons are dispersed)].

An ideal ray tracer simulating real life would instead send rays *from* the light sources *onto* the subjected surfaces, but this is in reality not practical and one would have to wait a very long time for an image to render; the probability of a light ray coming out of a source in a *random* direction, hitting an object, bouncing off that object in another *random* direction, and finally hitting the camera is *very* small. 

In real life, our human eyes still manage to see photons because there is just *too many* of them. Let's count how many photons per second are emitted by a typical \SI{100}{\watt} (\SI{100}{\joule\per\second}) lightbulb with an average wavelength of \SI{600}{\nano\meter}:

$$ E_{\textrm{photon}} = hf = \frac{hc}{\lambda} \approx \SI{3e-19}{\joule} $$

$$ \frac{P_{\textrm{lightbulb}}}{E_{\textrm{photon}}} = \frac{\SI{100}{\joule\per\second}}{\SI{3e-19}{\joule}} \approx \SI{3e20}{\per\second} $$

So, just for a normal lightbulb, approximately **300 billion billion** photons are emitted *every second*.

In comparison, a good computer has a power on the order of 10\ GFLOPS, that is 10\ billion operations per second. To come close to computing as many operations per seconds as photons emitted per second by a light bulb, a good computer would have to be 10 *orders of magnitude* faster.

### Backward tracing

This computational problem lead computer graphics developers to invent **backward tracing**, where light rays are traced *from* the camera back to the light source. In a best-case scenario, only *one* ray projection is needed per pixel.

The basic idea, explained visually in figure \ref{fig:raytracing}, is as follows:

- For each pixel of the screen, send a light ray from an imaginary point (the camera position) into the middle of that pixel
- If the ray hits an object, send a new ray (called *shadow* ray) in the direction of all light sources:
    + If the shadow ray hits no object in its way to the light source, then the object is lit by it. The light source's colour and the object's are mixed together and returned to the pixel
    + If the shadow ray hits another object, then the first object must be in shadow (the light source hits the other object first), so no colour is added
- If the hit object is reflective and the recursion limit is not reached, recursively trace a mirrored ray (the mirrored angle is 2 times the dot product between the original ray and the surface normal)

\customfig{img/ray-tracing.eps}{Backtracing light rays}{}{raytracing}

### Implemented algorithm

- Parallel via Java 8
- Find closest
- Keep distance in memory for falloff
- For each light
    - Find if intersection point is hit by light
    - Compute color
    - Bounce if reflective, recurse

\customfig{uml/rendering_activity.eps}{Rendering process activity diagram}{}{renderprocess}

### Bonus features

- Supersampling

### Primitives

Ray:

$$ \vec{o} + t\vec{r} $$

Sphere:
Ray-sphere intersection:

- Diagram


### Constructive solid geometry

\customfig{img/csg.png}{A piano foot obtained from CSG operations}{}{csgexample}

(Union)

(Difference)

(Intersection)

### Background projections

- Take other 3d diagram and apply projection
- List projections

### Depth of field

- Vector shifting diagram
- Aperture shape diagrams
- Effect of aperture
- Effect of focal distance
- Number of DOF samples
- List shapes with a few pics

### Materials
#### Procedural texturing
#### Bump mapping
#### UV Mapping

### Misc

- Gamma value
- Super-sampling factor
