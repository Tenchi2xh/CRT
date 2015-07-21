# User interface

\chapquote{A user interface is like a joke. If you have to explain it, it's not that good.}{Martin \textsc{LeBlanc}}{\textit{Iconfinder}}

The goal of this project from a GUI point of view was to mimick the professional look, feel and modularity of a standard IDE such as Netbeans or Eclipse. The interface has to be immediately understandable and appealing to the user.

The main window is composed of the following components:

- Code editor
- Renderer
- Property viewer
- Console
- Settings tab

The following sub-sections will present some of the highlights about the user interface and the libraries used to produce them.

## Netbeans API

The property tree and property editor were implemented using the Netbeans Platform API. This API provides many useful components used in the Netbeans IDE and with a little bit of tinkering, allowed for 

- Introspection, beans
- Property sheet, editor, hacks

## Live view

To provide a more direct feedback to the user and give him a feeling of where his entities are placed without going through the wait of a full render, a live view module was implemented. 

It consists mainly of two components: a `Scene` translator and an 60 frames per second OpenGL view.

The OpenGL view was implemented using jPCT, a library acting as a front-end for LWJGL, the most commonly used OpenGL library for Java. Given the scope of this project, a front-end was used to reduce development time: jPCT provides a complete framework with wrapper methods that do all the low-level OpenGL calls internally, enabling us to use simple methods like `Object3D.getCube(scale)` for creating a cube, instead of the following LWJGL code:

```{.java caption="Creating a cube with LWJGL"}
GL11.glBegin(GL11.GL_TRIANGLES);
GL11.glVertex3f( 0.0f, 1.0f, 0.0f);
GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
GL11.glVertex3f( 1.0f,-1.0f, 1.0f); 
// Repeated 5 times
GL11.glEnd();
```

And that is without colour or texture management, which jPCT provides easy methods for. Another useful aspect is the easy integration in Swing windows, albeit with some workaround having to be used when adding multiple viewports.

The conversion is pretty straight forward: we recursively go through a `Scene` object and whenever a supported entity or light is encountered, the jPCT equivalent creator method is invoked, and the created object is translated to its correct position.

During that conversion process, some sacrifices have to be made. The CSG objects cannot be easily translated to the polygonal world and computing the positions of the vertices of a CSG operation would be a project of its own right. Also, limitations of jPCT only allow for one light source for use with shadow mapping, which will make the preview even more different than the resulting ray-traced render.

Finishing and testing the live view module was a very exciting moment, because comparing the same `Scene` rendered once with our ray tracing engine and then with OpenGL would validate that all our algorithms are correct. And, indeed, all lines match, the perspective is correct.

TODO: comparison shot

After tweaking the module for a moment, the lack of a background in the previewed scenes was quickly made apparent. Because there are no rays in rasterization, we cannot just assign a colour to rays hitting infinity like we did with ray tracing (see section \ref{subsec:bg}).

So instead, we invented a clever hack that uses jPCT's implementation of sky boxes^[In video games, sky boxes are used for backgrounds and consist of cube much larger in magnitude than the rendered world and 6 textures are mapped on its faces.]: during the conversion process of a `Scene`, a secondary empty `Scene` is created that shares the same background. In its settings, the field of view is set to \SI{90}{\degree}, which is the angle needed to see only one face of the cube when the camera is placed in its centre.

We then proceed to render 6 separate images of the panorama background and use them for the jPCT sky box, which leads to an almost similar result to the regular spherical projection mapping used earlier:

TODO: skybox screenshot

- jPCT, LWJGL
- Conversion
- Sacrifices - only one shadow source, no CSG
- Hacks
- Problems with multiple viewports - no shadows

## Docking Frames

- General
- Difficulties