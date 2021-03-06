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

\customfig{uml/entity.eps}{The \texttt{\footnotesize Entity} class diagram}{}{entityclass}{}

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

### Camera \label{sec:camera}

A lit and populated scene still needs a window through which we will observe it: the `Camera` class defines the point of view of our rendered scene.

It has a **position** and a **direction** vector, as well as a **field of view** angle.

The **field of view** of a camera *how much* it sees from left to right, or from top to bottom of the image (in photography, this would represent the focal length of the objective). In CRT, the field of view is defined vertically, as an angle in radians. Varying this parameter has a *zoom* effect when lowered, while a big value makes more things visible on the screen.

To further add to the user's creative possibilities, several artistic features which aim to mimic real-life cameras were implemented:

- Depth of field (DOF), effect that creates a plane in which objects are sharp, and blurry outside, akin to a tilt-shift effect in photography.
- An aperture shape, which will be used to physically simulate the shape that *bokeh* will have (see figure \ref{fig:bokeh}).
- A focal distance, defining at which distance objects are sharp.

\customfig{img/bokeh.jpg}{Real-life \emph{bokeh}}{ --- the blurriness of out of focus objects will take the shape of the camera's aperture (pinhole). Here, the \emph{bokeh} is octogonal.}{bokeh}{Scott Tucker on Flickr}

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

\customfig{uml/rendering.eps}{Rendering process class diagram}{}{renderclass}{}

## Ray tracing \label{sec:raytracing}

A lot of elements were defined thus far --- but just what *is* ray tracing? First, a bit of history of computer graphics.

Traditionally, 3D computer graphics are rendered using a technique called **rasterisation**. Compared to ray tracing, rasterisation is extremely fast and is more suited for real-time applications, and takes advantage of years of hardware development dedicated to accelerating it.

In the rasterisation world, a 3D scene is described by a collection of **polygons**, usually triangles, defined by 3 three-dimensional vertices. A rasteriser will take a stream of such vertices, transform them into corresponding two-dimensional points on the viewer's monitor, and fill in the transformed two-dimensional triangles (with either lines, or colours).

\customfigB{img/rasterisation.png}{Rasterisation of a triangle}{ --- once the vertices have been projected on the screen, a discrete pixel is ``lit'' if its continuous centre is contained within the projected triangle's boundaries.}{}{Wikipedia}

Some effects of light observed in real life can be reproduce (or at least *mimicked*) on top of rasterisation. For example, if a polygon is not directly facing the camera (i.e. its *normal vector* is not parallel with the camera's direction), the resulting colour of the rasterised triangle will be darker.

However, the very nature of rasterisation makes it hard to implement other very common effects:

- To reproduce shadows, complicated stencil buffers must be used, along with a depth buffer computed by rendering a sub-scene from the point of view of the light source. This not only is complex but the results look very pixelated
- Refraction is very hard to reproduce. For a long time, raster application went without refraction effects and just have less opaque models. Nowadays, advanced pixel shaders use techniques similar to ray tracing

Ray tracing solves these issues, at the cost of being slower.

Instead of projecting things *from* the scene on the screen like with rasterisation, ray tracing is about sending rays *from* the screen *towards* the various elements of the scene.

But why this way? In real life, light sources send protons in all directions at random. Some of them hit objects, which *absorb* some of the energy from the photons (thus changing the perceived colour). The photons are then reflected, bouncing *off* the object with a mirrored angle of incidence^[Note that is angle is generally not exactly the mirrored incident angle and is in fact mostly random. Perfect surfaces like mirrors will indeed bounce off photons with a perfect angle (**specular** reflection), but most surfaces will scatter the protons in all directions (**diffuse** reflection --- that is why stones are not reflective like a mirror, their surface is *rough* so all incoming photons are dispersed)].

An ideal ray tracer simulating real life would instead send rays *from* the light sources *onto* the subjected surfaces, but this is in reality not practical and one would have to wait a very long time for an image to render; the probability of a light ray coming out of a source in a *random* direction, hitting an object, bouncing off that object in another *random* direction, and finally hitting the camera is *very* small.

In real life, our human eyes still manage to see photons because there is just *too many* of them. Let's count how many photons per second are emitted by a typical \SI{100}{\watt} (\SI{100}{\joule\per\second}) lightbulb with an average wavelength of \SI{600}{\nano\meter}:

\begin{equation} E_{\textrm{photon}} = hf = \frac{hc}{\lambda} \approx \SI{3e-19}{\joule} \end{equation}

\begin{equation} \frac{P_{\textrm{lightbulb}}}{E_{\textrm{photon}}} = \frac{\SI{100}{\joule\per\second}}{\SI{3e-19}{\joule}} \approx \SI{3e20}{\per\second} \end{equation}

So, just for a normal lightbulb, approximately **300 billion billion** photons are emitted *every second*. In *all possible* directions. And then hit objects, bounce in *all possible* directions again, hit other objects etc., and finally hit the observer's eye. Add to this the fact that because of the *inverse square law*, the further the observer is from a light source, the less photons per square metre he receives, in a quadratic fashion. This makes this model *very* impractical to use.

Just for comparison, a good computer has a power on the order of 10\ GFLOPS, that is 10\ billion operations per second. To come close to computing as many operations per seconds as photons emitted per second by a light bulb, a good computer would have to be $10^{10}$ times faster.

This computational problem has lead computer graphics developers to invent **backward tracing**, where light rays are traced *from* the camera back to the light source. In a best-case scenario, only *one* ray projection is needed per pixel.

This solve the difficulties of rasterisation previously mentioned in that the very nature of tracing rays makes it possible to apply the exact same formulas used in physics: the law of reflection, the Snell-Descartes law of refraction, Beer-Lambert law, the inverse square law, and so on. Also, shadows don't have to be drawn, they just exist --- light just "naturally" never reaches shadowed spots in a scene, so no light comes from it.

The basic idea of backward tracing, explained in details in the next section, is demonstrated visually in figure \ref{fig:raytracing}

\customfig{img/ray-tracing.eps}{Backtracing light rays}{.}{raytracing}{Wikipedia}

### Backward tracing implementation \label{sec:backtracing}

For every pixel on the screen, a **primary ray** is generated, then traced. Because each pixel's tracing is **independent** from one another, the process can be parallelized. This is easily done thanks to the new Java 8 API and its *streams*: after splitting the screen into blocks in a Java list `coords`, all we have to do is call:

```{.java caption="Java 8's easy parallelization"}
coords.parallelStream().forEach(
    (int[] c) -> processPixel(c, image, scene));
```

The process of tracing a ray takes the following steps:

1. Start with a black colour
2. Search for the entity closest to the ray's origin that intersects with the ray. If nothing is hit, return the background colour
3. For every light in the scene, send a **shadow ray** originated on the intersection point towards the light
    - Add the *ambient* factor of the light
    - If it hits nothing before reaching the light, add a mix of the light's colour and the object's to the current colour. The light's colour contribution is attenuated by two factors:
        * The less parallel the surface normal is with the incoming ray, the darker
        * The further the light had to travel, the darker (inverse square law)
    + If the shadow ray hits an object, it is in its shadow: no colour is added
4. If the surface's material has a reflective component, recursively trace a **reflection ray** in an angle symmetrical to the angle of incidence, and add the resulting colour
5. If the surface's material has a refractive component, recursively trace a **refraction ray** in an angle obtained with the Snell-Descartes law, and add the resulting colour

### Coordinate system

Like *POV-Ray*, *OpenGL*, *DirectX*, *Unity* and many others, this project opted for a **left-handed coordinate system** where the $z$ axis points inside the screen and not outwards.

\begin{figure}[!htbp]
\centering
\begin{tikzpicture}[]

  \draw[thin, dashed] (-1, 0, -2) -- (-1, 0, 2);
  \draw[thin, dashed] ( 1, 0, -2) -- ( 1, 0, 2);
  \draw[thin, dashed] ( 1, 0, -2) -- ( 1, 0, 2);
  \draw[thin, dashed] (-2, 0, -1) -- (2, 0, -1);
  \draw[thin, dashed] (-2, 0,  1) -- (2, 0,  1);
  \draw[thin, dashed] (-2, 0,  1) -- (2, 0,  1);

  \draw[->, thick] (0,0,0) -- (2,0,0) node[right]{$x$};
  \draw[->, thick] (0,0,0) -- (0,2,0) node[above]{$y$};
  \draw[->, thick] (0,0,0) -- (0,0,-3) node[above right]{$z$};

  \draw[dashed, thin] (0,0,0) -- (-2,0,0);
  \draw[dashed, thin] (0,0,0) -- (0,0,2);

\end{tikzpicture}
\caption{Left-handed coordinate system}
\label{fig:coords}
\end{figure}

For graphical composition, having an inverted $z$ axis is (for some people) more intuitive: if the origin is located at the bottom-left of the screen, $x$ goes *right*, $y$ goes *up*, and $z$ goes *inside* the screen.

This system choice incurs a small consideration when doing linear algebra, but can be converted any time between both systems. The only real thing to change is the way the *cross product* behaves and invert some results.

### Ray generation

Before tracing the path of a ray, we need to *generate* it. A **ray** has an origin ($\vec{o}$), a length ($t$) and a direction ($\vec{d}$). Its equation is thus:

\begin{equation} \label{eq:ray} \vec{r} = \vec{o} + t\vec{d} \end{equation}

The initial rays we begin with when ray-tracing are called **primary rays**. For a standard *pinhole* projection, they originate (before camera transformation) at $\vec{o} = \vec{0}$ and each ray points towards the centre of a pixel situated on a *virtual screen* the same resolution as the desired output, situated 1 unit away on the $z$ axis.

\begin{figure}[!htbp]
\centering
\begin{tikzpicture}[scale=0.9]
\coordinate (o) at (-3, 0, 0);

\draw[fill] (o) circle (1pt) node[left]{$\vec{o}$};
\draw[thick] (o) -- (0.8, 0, 0);
\draw[thick, dashed] (0.8, 0, 0) -- (2, 0, 0);
\draw[->, thick, >=latex] (2, 0, 0) -- (4, 0, 0) node[right]{$\vec{d}$};

\draw[very thin] (2, -1.0, -3.0) -- ++(0, 0, 6);
\draw[very thin] (2, 1.0, -3.0) -- ++(0, 0, 6);
\draw[very thin] (2, 3.0, -3.0) -- ++(0, 0, 6);

\draw[very thin] (2, -1.0, -3.0) -- ++(0, 4, 0);
\draw[very thin] (2, -1.0, -1.0) -- ++(0, 4, 0);
\draw[very thin] (2, -1.0, 1.0) -- ++(0, 4, 0);
\draw[very thin] (2, -1.0, 3.0) -- ++(0, 4, 0);

%\draw[loosely dotted] (o) -- (2, -3, -3);
%\draw[loosely dotted] (o) -- (2, 3, -3);
%\draw[loosely dotted] (o) -- (2, -3, 3);
%\draw[loosely dotted] (o) -- (2, 3, 3);

\coordinate (p4) at (2, 0, -2);
\coordinate (p5) at (2, 0, 0);
\coordinate (p6) at (2, 0, 2);
\coordinate (p7) at (2, 2, -2);
\coordinate (p8) at (2, 2, 0);
\coordinate (p9) at (2, 2, 2);

\draw[fill] (p4) circle (0.5pt);
\draw[fill] (p5) circle (1pt);
\draw[fill] (p6) circle (0.5pt);
\draw[fill] (p7) circle (1pt) node[above]{$\vec{p}_{i,j}$};
\draw[fill] (p8) circle (0.5pt);
\draw[fill] (p9) circle (0.5pt);

\draw[] (o) -- (2, 3, 3);
\draw[->, >=latex, dashed] (2, 3, 3) -- (p7);
\draw[dotted] (2, 0, 0) -- (2, 0, -2);
\draw[dotted] (p7) -- (2, 0, -2);
\draw[dotted] (o) -- (2, 0, -2);

\draw[decorate, decoration={brace, amplitude=5pt}, xshift=2pt] ([xshift=2pt]p7) -- node[right, xshift=4pt, yshift=-2pt]{$n_y$} (2, 0, -2);

\draw[decorate, decoration={brace, amplitude=5pt, mirror}, xshift=2pt] (2, 0, 0) -- node[below right, xshift=2pt]{$n_x$} (2, 0, -2);

\end{tikzpicture}
\caption{Primary ray}
\end{figure}

The first step is to normalize the coordinates of the targeted pixel to be between $-1$ and $1$. We add $0.5$ in both directions so that the ray "aims" towards its centre, if it was $1$ unit long and $1$ unit high.

```{.java caption="Pixel coordinates normalization"}
double nX = (2 * ((x + 0.5) / settings.width) - 1);
double nY = (1 - 2 * ((y + 0.5) / settings.height));
```

The next is to take in account the camera's *field of view* (see section \ref{sec:camera}): the wider the angle, the more we will see left and right, up and down. To reflect this, we have to multiply both normalized pixel coordinates by a **FOV factor**.

Because the FOV value corresponds to the vertical FOV, we also need to multiply the $x$ coordinates by the screen's ratio.

The FOV factor is easily calculated: if $\alpha$ is the FOV angle and because $\|\vec{d}\| = 1$, simple trigonometry tells us that the factor we need to multiply our normalized coordinates by is $\tan(\frac{\alpha}{2})$:

\begin{figure}[!htbp]
\centering
\begin{tikzpicture}

\draw[->, thick, >=latex] (0, 0) node[left]{$\vec{o}$} -- node[below]{$\vec{d}$}(2, 0);
\draw (0, 0) circle (2);
\draw[thick] (2, 3) -- (2, 0);
\draw[thick] (2, -3) -- node[right]{\rotatebox{90}{\textit{screen}}} (2, 0);
\draw[decorate, decoration={brace, amplitude=5pt}, xshift=1mm] (2, 3) -- node[right, xshift=1mm]{$\tan(\frac{\alpha}{2})$}(2, 0);

\draw[dashed] (0, 0) -- (2, 3);
\draw[dashed] (0, 0) -- (2, -3);
\draw (0.5, 0) arc (0:56.30:0.5) node at (28.15:0.7) {$\frac{\alpha}{2}$};

\end{tikzpicture}
\caption{Finding the FOV factor}
\label{fig:fov}
\end{figure}

These two operations leave us with primary rays originating from $\vec{0}$, looking at a screen centred at $(0, 0, 1)$. To transform these rays and put them where the camera actually is, we could translate and rotate them using a *transformation matrix*, but it is simpler to just add the camera's *up* and *right* component multiplied by the normalized and FOV-adjusted coordinates we computed earlier.

If we sum up, these are all the steps needed for primary ray generation (code from the `Tracer` class):

```{.java caption="Primary ray generation"}
double nX = (2 * ((x + 0.5) / settings.width) - 1);
double nY = (1 - 2 * ((y + 0.5) / settings.height));

double camX = nX * settings.fovFactor * settings.ratio;
double camY = nY * settings.fovFactor;

Vector3 rightComp = camera.getRight().multiply(camX);
Vector3 upComp = camera.getUp().multiply(camY);
direction = direction.add(rightComp).add(upComp).normalize();

Ray primary = new Ray(direction, camera.getPosition());
```

### Primitives

Next in the pipeline after generating a primary ray is to check whether or not it *intersects* with any of the *primitives* present in the scene, and take the closest one.

Checking if a ray intersects with a primitive amounts to put both their equations in one and *solve it*. In this section we will see how to compute a ray-sphere intersection and find the intersection points if they exist^[In the code, more than that is done because other parts of the ray-tracer need intersection normals].

In vector notation, the equation of a sphere is

\begin{equation} {\| \vec{r} - \vec{c} \|}^2 = R^2 \end{equation}

where $\vec{r}$ is a point on the sphere, $\vec{c}$ the centre of the sphere and $R$ its radius. By substituting $\vec{r}$ with the ray equation (\ref{eq:ray}), we get

\begin{equation} {\| \vec{o} + t\vec{d} - \vec{c} \|}^2 = R^2 \end{equation}

which, when expanded and rearranged, gives

\begin{equation} t^2(\vec{d} \cdot \vec{d}) + 2t(\vec{d} \cdot (\vec{o} - \vec{c})) + (\vec{o} - \vec{c}) \cdot (\vec{o} - \vec{c}) - R^2 = 0 \end{equation}

We can now solve this quadratic equation of the form $at^2 + bt + c = 0$ for $t$, where

\begin{equation} a = \vec{d} \cdot \vec{d} \end{equation}
\begin{equation} b = 2(\vec{d} \cdot (\vec{o} - \vec{c})) \end{equation}
\begin{equation} c = (\vec{o} - \vec{c}) \cdot (\vec{o} - \vec{c}) - R^2 \end{equation}

Using the method of the discriminant, we get:

\begin{equation} t = \frac{-b \pm \sqrt{b^2 - 4ac}}{2a} \end{equation}

Next, depending on whether or not there is zero, one or two solutions, we can find the intersection points by injecting $t$ back into the ray equation, check whether or not the ray was shot from inside the sphere, compute the normals, etc. These informations will be useful later when we will be doing *CSG operations*.

### Light calculations

As quickly described in section \ref{sec:backtracing}, colours are computed in an *additive* fashion, depending on several factors such as light source *angle*, *intensity* and material properties.

There are several colour components derived from the lights present in a scene: ambient, diffuse and specular. This method of computing light components is called the **Blinn--Phong shading model**.

\begin{figure}[!htbp]
\centering

\subfloat[Ambient]{\centering\makebox[.24\linewidth]{
\includegraphics[width=0.20\linewidth,keepaspectratio]{img/phong_ambient.png}}}
\subfloat[Diffuse]{\centering\makebox[.24\linewidth]{
\includegraphics[width=0.20\linewidth,keepaspectratio]{img/phong_diffuse.png}}}
\subfloat[Specular]{\centering\makebox[.24\linewidth]{
\includegraphics[width=0.20\linewidth,keepaspectratio]{img/phong_specular.png}}}
\subfloat[Combined]{\centering\makebox[.24\linewidth]{
\includegraphics[width=0.20\linewidth,keepaspectratio]{img/phong_combined.png}}}

\label{fig:phong}
\caption[Phong shading model]{Phong shading model --- light components are computed in steps.}
\end{figure}

First off, let's define the **inverse square law**, which we will use for computing the following components. In physics, the amount of light received from a light source at a given distance is *inversely proportional* to the square of the distance:

\begin{equation} I \propto \frac{1}{t^2} \end{equation}

In the following diagram, we can see that effect represented in three dimensions: every square is the same area, but from a greater distance, the same amount of rays hit more squares and thus, each square gets less light.

\customfig{img/isl.png}{Inverse square law}{}{isl}{Wikipedia}

In the code, the recursive tracing functions keeps track of all the distance that the starting primary ray travelled, and computes the ISL factor by dividing the light's *falloff* factor by the square of the total distance squared.

The first component of the Blinn--Phong model is trivial: **ambient** light is just a fraction of the light's colour that is added to any point on the scene, whether or not it is in shadow:

\begin{equation} \vec{c}_\textrm{a} = l_\textrm{a} \vec{l}_\textrm{c} \end{equation}

where $l_\textrm{a}$ is the light's ambient factor and $l_\textrm{c}$ its colour. Note that this is a quick and dirty *trick* to simulate global illumination which would otherwise be costly, inherited from more standard rendering techniques like in OpenGL for example.

**Diffuse** light is the amount of light that is scattered by the material when hit by a light source. It simulates the fact that for a point on a material's surface, the more a light source's *direction* is aligned with its *normal*, the more the point gets illuminated by the light source. Let's observe that in the following diagram:

\begin{figure}[!htbp]
\centering

\subfloat[Rays parallel with normal]{%
\centering
\makebox[.45\linewidth]{
\begin{tikzpicture}[rotate=-45, scale=0.7]

  \draw[dashed] (0.0, 0.0) -- (1.5, 0.0);
  \draw[dashed] (0.0, 1.0) -- (1.5, 1.0);
  \draw[dashed] (0.0, 2.0) -- (1.5, 2.0);

  \draw[->, >=latex] (1.5, 0.0) -- (3.0, 0.0);
  \draw[->, >=latex] (1.5, 1.0) -- (3.0, 1.0);
  \draw[->, >=latex] (1.5, 2.0) -- (3.0, 2.0);

  \draw[<->] (0.5, 1.0) -- node[midway, below right]{$x$} (0.5, 0.0);

  \draw[very thick] (3.0, -0.5) -- (3.0, 2.5);
  \fill[pattern=vertical lines] (3.0, -0.5) rectangle (3.2, 2.5);
\end{tikzpicture}}}%
\subfloat[Rays at \SI{45}{\degree} with normal]{%
\centering
\makebox[.45\linewidth]{
\begin{tikzpicture}[rotate=-45, scale=0.7]

  \draw[->, >=latex, thick] (3.0, 1.0) -- ++(-2, 2) node[above]{$\vec{n}$};

  \draw[dashed] (0.0, 0.0) -- (1.5, 0.0);
  \draw[dashed] (0.0, 1.0) -- (1.5, 1.0);
  \draw[dashed] (0.0, 2.0) -- (1.5, 2.0);

  \draw[->, >=latex] (1.5, 0.0) -- (2.0, 0.0);
  \draw[->, >=latex] (1.5, 1.0) -- (3.0, 1.0);
  \draw[->, >=latex] (1.5, 2.0) -- (4.0, 2.0);

  \draw[<->] (0.5, 0.0) -- node[midway, above, fill=white, inner sep=1.1pt]{$\sqrt{x}$} ++(1.0, 1.0);

  \draw[very thick] (1.5, -0.5) -- (4.3, 2.3);
  \fill[pattern=north east lines, rotate=-45] (1.4, 0.7) rectangle ++(0.3, 4.0);
  \fill[fill=white] (2.5, -1) rectangle ++(0.1, 0);
\end{tikzpicture}}}%
\caption{Surface angle with incoming rays}
\label{fig:lightangle}
\end{figure}

As we can see, if our rays are separated by a distance $x$ and hit a surface whose normal is parallel with them, their distance on the surface is still $x$, whereas if the surface normal is at a \SI{45}{\degree} angle with the rays, they are separated by a distance of $\sqrt{x}$ --- reducing the density of photons per area, and thus the surface is *darker*.

An *attenuation* factor is first computed by taking the *dot product* of the surface normal and the light's direction vector, giving the cosine of the angle. We then proceed to multiply this factor with the ISL and the light's colour, then multiply this result with the surface's material colour:

\begin{equation} \vec{c}_\textrm{d} = \Big[\vec{l}_\textrm{c} I (\vec{l}_\textrm{d} \cdot \vec{n})\Big] \cdot (\vec{m}_\textrm{c} m_\textrm{d}) \end{equation}

Lastly, we have the **specular** component which emulates shininess. This is also not physically accurate; it is a *trick* to give light sources more "width" instead of just being single infinitesimally small points, so that their reflections can be seen on diffuse shiny surfaces.

The specular factor of any given point on a surface depends on how much the light source's reflected ray ($\vec{l'}$) on that point is aligned with the viewing direction ($\vec{r}$), i.e. the angle between the reflected light ray and the incoming primary ray:

\begin{figure}[!htbp]
\centering\begin{tikzpicture}[scale=0.7]

  \draw[->, >=latex, thick] (0, 0) -- ++(0, 3) node[above]{$\vec{n}$};
  \draw[->, >=latex] (60:3) node[above right]{$\vec{l}$} -- (0, 0);
  \draw[->, >=latex, dashed] (0, 0) -- (120:3) node[above left]{$\vec{l'}$};
  \draw[->, >=latex, thick] (150:3) node[above left]{$\vec{r}$} -- (0, 0);

  \draw (120:1) arc (120:150:1) node at (135:1.3) {$\alpha$};

  \draw[very thick] (-2, 0) -- (2, 0);
  \fill[pattern=north east lines] (-2, 0) rectangle (2, -0.2);

\end{tikzpicture}

\caption{Angle between reflected light ray and primary ray}
\label{fig:angles}
\end{figure}

The reflected ray is computed as follows:

\begin{equation} \label{eq:reflect} \vec{l'} = \vec{l} - 2(\vec{l}\cdot\vec{n})\vec{n} \end{equation}

The specular factor is then computed by taking the cosine of the angle (*aka* dot product) between the reflected ray and the primary ray to the power $m_\textrm{sh}$, the material's shininess factor. The formula for the specular component is then:

\begin{equation} \vec{c}_\textrm{s} = (\vec{l'} \cdot \vec{r})^{m_\textrm{sh}} m_\textrm{s} I (\vec{l}_\textrm{c} \cdot \vec{m}_\textrm{c}) \end{equation}

Summing up, the total colour of a given point using the Blinn--Phong model is

\begin{equation} \vec{c} = \vec{c}_\textrm{a} + \vec{c}_\textrm{d} + \vec{c}_\textrm{s} \end{equation}

To this, we can add reflectivity and refraction, by recursively tracing the reflected / refracted rays.

In this project, two types of light sources were implemented: *parallel* lights and *point* lights. As explained on the next figure, parallel lights like the sun represent a light source that is infinitely far away rendering its rays virtually parallel. This implies that the ISL is no longer in effect^[By looking at figure \ref{fig:isl}, we can see that if all rays were indeed parallel, all the squares would receive the same amount of rays] whereas with the point light, we can see on the figure that just by going a bit further down the surface the light vectors are longer and more spread out.

\begin{figure}[!htbp]
\centering

\subfloat[Parallel light]{%
\centering
\begin{tikzpicture}[rotate=-45, scale=0.7]

  \draw[dashed] (0.0, 0.0) -- (1.5, 0.0);
  \draw[dashed] (0.0, 0.5) -- (1.5, 0.5);
  \draw[dashed] (0.0, 1.0) -- (1.5, 1.0);
  \draw[dashed] (0.0, 1.5) -- (1.5, 1.5);
  \draw[dashed] (0.0, 2.0) -- (1.5, 2.0);

  \draw[->, >=latex] (1.5, 0.0) -- (3.0, 0.0);
  \draw[->, >=latex] (1.5, 0.5) -- (3.0, 0.5);
  \draw[->, >=latex] (1.5, 1.0) -- (3.0, 1.0);
  \draw[->, >=latex] (1.5, 1.5) -- (3.0, 1.5);
  \draw[->, >=latex] (1.5, 2.0) -- (3.0, 2.0);

  \draw[very thick] (3.0, -0.5) -- (3.0, 2.5);
  \fill[pattern=vertical lines] (3.0, -0.5) rectangle (3.2, 2.5);
\end{tikzpicture}}%
\hspace{0.1\linewidth}%
\subfloat[Point light]{%
\centering
\begin{tikzpicture}[rotate=-45, scale=0.7]

  \coordinate (x) at (1.0, 1.0);
  \draw[fill] (x) circle (2pt) node[above right]{$\vec{o}$};
  \draw[->, >=latex] (x) -- ++( 30:2.3cm);
  \draw[->, >=latex] (x) -- ++(  0:2cm);
  \draw[->, >=latex] (x) -- ++(-30:2.3cm);
  \draw[->, >=latex, dashed] (x) -- ++(-60:1cm);
  \draw[->, >=latex, dashed] (x) -- ++(-90:1cm);
  \draw[->, >=latex, dashed] (x) -- ++(-120:1cm);
  \draw[->, >=latex, dashed] (x) -- ++(-150:1cm);
  \draw[->, >=latex, dashed] (x) -- ++(-180:1cm);
  \draw[->, >=latex, dashed] (x) -- ++(-210:1cm);

  \draw[very thick] (3.0, -0.5) -- (3.0, 2.5);
  \fill[pattern=vertical lines] (3.0, -0.5) rectangle (3.2, 2.5);

\end{tikzpicture}}
\caption{Light source types}
\label{fig:lighttypes}
\end{figure}

In the code, this is implemented by making parallel light sources always return the same direction vector, whereas point light sources compute each direction vector by subtracting the surface point to their origin.

### Constructive solid geometry \label{sec:csg}

CSG operations are similar to *set operations*, but apply to primitive solids or results of other CSG operations. They are of three kinds: union, intersection and difference. In the code, CSG operations inherit from `Entity` and must provide an `intersect(Ray r)` method.

The first CSG operation is the **union**. It is very trivial: the intersection of a ray and and a union of objects is just the intersection point of all the objects that is *closest* to the ray's origin.

\customfigB{img/union.png}{CSG union between a cube and a sphere}{}{csgunion}{}

Next is the **intersection**, where only points that are contained in both parent objects are present in the scene. The way we implemented CSG intersection is to continue tracing the path of a ray when it hits one of both parent objects, and see if the ray also hits the second object before exiting the first one. 

\customfigB{img/intersection.png}{CSG intersection between a cube and a sphere}{}{csgintersection}{}

Finally, the **difference** only shows part of the first object that are also not part of the second. There are multiple cases:

- The ray hits the first object first. If the second object is not hit, the ray is traced further inside the first and ends when it exits it or hits the second object.
- The ray hits the second object first: the ray is traced further until it hits the first object.

\customfigB{img/difference.png}{CSG difference between a cube and a sphere}{}{csgdifference}{}

Using only those three basic processes, one can combine simple primitives and iteratively produce complex shapes, as we can see in figure \ref{fig:csgexample} which models an upright piano foot produced in POV-Ray, resulting from multiple CSG operations: cylinders are subtracted twice from boxes and tori quarters are used to fill the gaps between the two resulting boxes.

\customfigB{img/csg.png}{A piano foot obtained from CSG operations}{}{csgexample}{}

### Background projections \label{subsec:bg}

Rendering purely mathematical primitive volumes is nice and fun but a little dull without an interesting background behind them. To add more depth to the scenes, backgrounds were implemented in the `Background` object. It provides three modes:

- Solid colour
- Horizon and zenith gradient
- Spherical panoramic projection

The first option is very basic and only provides a base colour for all rays that hit infinity.

The second option is more advanced and is a good compromise if an artist does not yet have a panoramic picture for his background: it generates a *gradient* from horizon to zenith by interpolating between the two colours depending on the angle the ray has compared to the $xz$ plane.

The interesting one is the latter, which takes a \SI{360}{\degree} *panorama* and a direction vector, and returns the colour at which the vector would point towards if the panorama was wrapped around the scene on an infinitely big sphere.

The way the pixel coordinates are computed on the panorama picture is as follows: using `atan2()`, the angle of the vector projected on the $xz$ plane (*yaw*) is mapped to the $x$ coordinate on the picture, and the angle of the vector projected on the $yz$ plane (*pitch*) is mapped to the $y$ coordinate. The final colour is then bilinearly interpolated and returned to the ray tracer.

\begin{figure}[!htbp]
\centering
\subfloat[Solid colour]{\centering\makebox[.33\linewidth]{
\includegraphics[width=0.28\linewidth,keepaspectratio]{img/bgsolid.png}}}
\subfloat[Gradient model]{\centering\makebox[.33\linewidth]{
\includegraphics[width=0.28\linewidth,keepaspectratio]{img/bgzenith.png}}}
\subfloat[Panorama projection]{\centering\makebox[.33\linewidth]{
\includegraphics[width=0.28\linewidth,keepaspectratio]{img/bgpanorama.png}}}
\caption[All 3 different background types]{Different supersampling values}
\label{fig:ssaa}
\end{figure}

### Depth of field

To simulate a *real camera*, the implemented ray tracing model supports **depth of field** simulation. It provides an additional layer of realism and dynamism to the rendered pictures by making all out of focus objects blurred in a physically realistic way.

In order to achieve this effect, the ray tracing model is modified: instead of having all the rays originating from one point, in this model the rays can originate from any point on a *disc* (mimicking the fact that a camera has a disc-shaped aperture hole and not a single point hole).

\begin{figure}[!htpb]
\centering
\tikzset{middlearrow/.style={
        decoration={markings,
            mark= at position 0.5 with {\arrow{#1}} ,
        },
        postaction={decorate}
    }
}
\begin{tikzpicture}[]
  \draw[middlearrow={latex}] (-1.0, 0.0) -- (0.0, 3.0);
  \draw[middlearrow={latex}] (0.0, 0.0)  -- (0.0, 3.0);
  \draw[middlearrow={latex}] (1.0, 0.0)  -- (0.0, 3.0);

  \draw[->, >=latex] (0.0, 3.0) -- (-1.0, 6.0);
  \draw[->, >=latex] (0.0, 3.0) -- (0.0, 5.0);
  \draw[->, >=latex] (0.0, 3.0) -- (1.0, 6.0);

  \draw[dashed] (-2.0, 3.0) -- (2.0, 3.0) node[right]{Focal plane};
  \draw[thick] (0,0) ellipse (1.0 and 0.2) node[right, xshift=1.0cm]{Aperture disc};

  \draw[fill] (0.0, 3.0) circle (1.2pt) node[right]{$\vec{p'}$};
  \draw[fill] (0.0, 5.0) circle (1.2pt) node[above]{$\vec{p}$};
\end{tikzpicture}
\caption{Disc-shaped aperture}
\label{fig:dofdiagram}
\end{figure}

As can be seen on the diagram, the primary rays can be generated anywhere on the origin disc, and aim at the desired object, projected on the focal along the centre axis from the disc. If the object lies on that focal plane, all rays originating from the disc will land on the *same spot* on the object.

However, as shown in the diagram, if the object is further away from the focal plane, rays originating from different location on the disc will meet on the focal plane but then *diverge* until they reach their destination, which will not always be the object aimed at (in the example, only the middle ray hits the point $\vec{p}$, and the other two travel further behind).

By *averaging* the colour resulting from these rays, the final colour will either be the object's precise colour if it lies on the focal plane, or a mixture of the object's colour and the background around it. This effectively creates a depth of field blur.

\customfig{img/dofexample.png}{Example of in-engine depth of field}{, here with a very open aperture size, which makes things appear smaller.}{}{}

Three factors have an effect on how the depth of field blur will look like: aperture size, focal distance and aperture shape.

**Aperture size** determines the "thickness" of the area that will be in focus (literally the *depth* of the visible field), and will in practice represent the radius of the aperture disc (or other regular polygon). The *smaller* this disc is, the *more* it will be like a point-source, and thus resembling more the normal model with no depth of field. The bigger it is, the shallower the area where objects are in focus will be (and the blurrier out-of-focus objects will appear).

The *focal distance*'s effect is very straight forward: it controls the distance at which the focal plane will reside, in which objects are in focus and not blurred. Finally, the aperture shape will determine the shape of the *bokeh* (see section \ref{sec:camera}). In the aperture of a real camera, a very big number of light rays go through and hit almost all possible points on the disc. When ray-tracing, we can only trace a limited number of rays, and will choose their location *randomly*.

For this, we need to model shapes, which will provide a method to obtain a uniformly distributed random point from inside their boundaries. A lazy but efficient approach is to take random points from a square, which only consists of having two random float numbers ranging from 0 to 1 as $x$ and $y$ coordinates. A perfect model uses a disc, which is the shape cameras try to obtain. Efficient and correct algorithms to generate a random point in a circle already exists and were implemented in the project.

Because real life cameras need their aperture size to change for different exposures, their aperture mechanism is constructed of a number of blades that can change the size of the hole they create:

\customfig{img/blades.jpg}{Aperture blades}{, also called iris, here with a pentagonal shape}{}{Nayu Kim on Flickr}

In practice, this means that the resulting shape is never a perfect circle, but is approximated with a regular *polygon* such as a pentagon for cheap lenses or octagons for luxury lenses. Professor Jean-François \textsc{Hêche} devised an efficient algorithm for generating random coordinates inside a regular polygon, by dividing the work in sub steps. First, generate a point inside a square and map it to a triangle. Because all regular polygons can be split into triangles, the last step is to randomly choose which triangle contains the point and rotate the coordinates to that position. This gives us a smooth, shaped *bokeh*:

\begin{figure}[!htbp]
\centering
\subfloat[Generating 1000 points in a pentagon takes less then \SI{10}{\milli\second}]{\centering\makebox[.45\linewidth]{
\includegraphics[width=0.44\linewidth,keepaspectratio]{img/random.png}}}
\qquad
\subfloat[Pentagonal \textit{bokeh} in a ray-traced scene]{\centering\makebox[.45\linewidth]{
\includegraphics[width=0.44\linewidth,keepaspectratio]{img/inengine.png}}}
\caption[In-engine \textit{bokeh}]{In-engine \textit{bokeh}}
\label{fig:bokeh2}
\end{figure}

The depth of field model isn't very efficient performance-wise: because the source of the rays is not a point any more, we need to trace more than one ray per pixel on the screen. The ray origins are to be chosen randomly on the aperture shape, and their number is chosen by the user at render time. If this number is too low, the resulting image will look very *noisy*, as seen in the following diagram. A number too big will multiply the render time linearly, and by adding supersampling (see section \ref{subsec:ss}), the render time will become very long. Empirically, a good number of samples for depth of field at normal resolutions has been found to be between 32 and 64.

\customfig{img/noisy.png}{Noisy DOF}{}{}{}

### Materials

The following concepts were not implemented in the final project but were studied. It is a very interesting domain on which could require a separate project on its own to fully master.

Plain colours won't be enough for user creativity to fully bloom. The "traditional" rasterisation way to address this issue is to map external pictures to polygon --- or in our case, primitives. But another way is through **procedural texturing**, which consists of algorithmically generating textures.

One of the basis for procedural texturing lies within the *Perlin noise*, a type of gradient noise obtained by interpolating several grids of different sizes filled with random greyscale colours.

\customfigC{img/perlin.png}{2D Perlin noise}{}{}{}

A continuous three-dimensional version of the Perlin noise constitutes a primitive texture for procedural texturing. This volume of random noise has a contiguous aspect, and so can be sliced anywhere without showing any seams. Then, by arbitrarily applying operations, one can transform this 3D noise into interesting textures.

The following example uses Perlin noise data to slightly alter the direction of the surface normal vectors of a sphere. This perturbation causes the spheres to appear rough in a realistic fashion.

\customfig{img/bumpmapping.jpg}{Bump mapping generated from perlin noise}{}{}{KTH Royal Institute of Technology}

Other primitives can be used for texturing. For example, the following example uses a simple wave pattern that is easily mappable to a 3D space to produce a wooden texture. By applying random turbulences and varying the period, the very uniform wave pattern is transformed into a realistic looking wooden texture.

\begin{figure}[!htbp]
\centering
\subfloat[Simple wave pattern]{\centering\makebox[.33\linewidth]{
\includegraphics[width=0.28\linewidth,keepaspectratio]{img/waves.png}}}
\subfloat[Some turbulences are applied]{\centering\makebox[.33\linewidth]{
\includegraphics[width=0.28\linewidth,keepaspectratio]{img/noisewood.jpg}}}
\subfloat[The 3D function is mapped to the colour of a primitive]{\centering\makebox[.33\linewidth]{
\includegraphics[width=0.28\linewidth,keepaspectratio]{img/wood.png}}}
\caption[Procedurally generated wood texture]{Procedurally generated wood texture}
\end{figure}

### Supersampling \label{subsec:ss}

In order to produce quality pictures, **supersampling** was implemented. It is a spatial anti-aliasing^[Aliasing is seen in most edges, which appear jagged and pixelated.] method which works by tracing multiple rays per pixel instead of just one, then *averaging* the resulting colours, providing a much more accurate final colour for a given pixel.

There are multiple ways to select coordinates inside a pixel: one could choose $n$ random points inside each pixels, or choose a more advanced sampling pattern^[See http://en.wikipedia.org/wiki/Supersampling#Supersampling_patterns], but for the purpose of this project, a simple *grid* pattern was used.

\begin{figure}[!htbp]
\centering
\subfloat[No supersampling]{\centering\makebox[.33\linewidth]{
\includegraphics[width=0.28\linewidth,keepaspectratio]{img/ss1.png}}}
\subfloat[$2\times 2$ SSAA]{\centering\makebox[.33\linewidth]{
\includegraphics[width=0.28\linewidth,keepaspectratio]{img/ss2.png}}}
\subfloat[$4\times 4$ SSAA]{\centering\makebox[.33\linewidth]{
\includegraphics[width=0.28\linewidth,keepaspectratio]{img/ss3.png}}}
\caption[Different supersampling values]{Different supersampling values}
\label{fig:ssaa}
\end{figure}

In figure \ref{fig:ssaa}, we can see that the bigger grid we use, the more refined the final picture is. This, however, comes at a big cost: with a $4\times 4$ grid, a render will likely take at least 16 times as much time to render.

\begin{figure*}[ht]
  \includegraphics[width=\hsize,keepaspectratio]{img/pebbles.jpg}
  \caption[Example of procedural textures]{With procedural texturing, the possibilities are endless. This picture by Jonathan \textsc{Hunt} was rendered with POV-Ray, which provides a very extensive array of tools for procedural texturing.}
\end{figure*}

\newpage

### Animations \label{sec:anim}

An interesting thing to do with ray tracing is **animations**. The idea was suggested by prof. Pier \textsc{Donini} and fits nicely in the theme of the project.

Because the production paradigm of the application is centered around the *scripting*, we had to find an efficient way to enable the user to animate his scene. This was done in a way inspired by physics: introducing a pre-defined user variable called $t$, like time.

At each frame, the value of $t$ is incremented and the user can use it in multiple ways:

- For a linear motion of an object, one of its coordinates can be expressed with `p0 + t*step`
- For a circular motion of an object, two of its coordinates can be expressed with `cos(t*speed)` and `sin(t*speed)`
- To give objects some weight, the user can use physics formulaes to describe the movement of objects along the $y$ axis, with for example `p0 + 0.5*9.81*t*t`
- The constant can be used to change the distance of the focal plane, the aperture size, or the field of view over time.
- Many other ways up to the user's creativity

To use the animation mode, the user can switch to the "Animation" tab, choose how many frames to render and press "Render". After rendering the last frame, the user can go back and forth using the slider bar, and the animation is automatically saved in the GIF format in the application folder.

An example of an animation can be found in the `demos/` folder, under the file name `demoAnimation.crt`.


\begin{figure*}[ht]
  \includegraphics[width=\hsize,keepaspectratio]{img/zfighting.png}
  \caption[Depth glitch]{Depth glitch}
\end{figure*}

\newpage 

### Miscellaneous

During the development of the ray tracing engine, one type of bug was encountered multiple times in all parts of the module, related to all the depth calculations.

This glitch appears in two distinct cases:

- During primitive intersection computations, some comparisons between double precision floating numbers have to be made, and due to their precision, the comparison can produce a false result and collisions happen.
- When the intersection point between a ray and a primitive is precisely on the primitives surface, the shadow ray bouncing off the surface searching for a light source will immediately hit the point where it started, making the engine think the point is in shadow where it could have reached the light source.

Thankfully, a simple but tedious fix has been found: defining a global $\varepsilon$ value, and using it in all occurrences of double comparisons and hit confirmation.

In the code, this value has been arbitrarily set to \SI{1e-10} and is stored in `Tracer.E`. 
