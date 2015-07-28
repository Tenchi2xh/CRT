# User interface

\chapquote{A user interface is like a joke. If you have to explain it, it's not that good.}{Martin \textsc{LeBlanc}}{\textit{Iconfinder}}

The goal of this project from a GUI point of view was to mimic the *professional* look, feel and modularity of a standard IDE such as *Netbeans* or *Eclipse*. The interface has to be immediately understandable and appealing to the user.

The main window is composed of the following components:

- Code editor
- Renderer
- Console
- OpenGL live view
- System graphs

The following sub-sections will present some of the highlights about the user interface and the libraries used to produce them.

## Code editor

\customfig{img/editor.png}{Screenshot of the code editor}{}{}{}

The code editor was implemented using only Swing and AWT, and contains multiple features found in good text editors such as:

- Syntax highlighting: by using ANTLR's lexer, we can determine the token type of the words contained in the editor and style them accordingly with Swing highlighters. Also, unexpected tokens can be highlighted in red.
- When a word is selected, all occurrences of the same word will be circled across the editor, allowing to quickly find a user variable for example.
- The lines are numbered, with the current line highlighted.

## Console

The addition of a console inside the final IDE proved to be very useful, for both development and common use. It displays all messages sent to `stdout` as well as `stderr`, in red. 

To catch those channels and redirect them to the GUI console, a custom `PrintStream` subclass was created that prints messages to a `JTextPane`, and using the following trick, `stdout` and `err` were redirected to that custom `PrintStream`:

```{.java caption="Redirecting standard out and error"}
JTextPane console;
out = new SimpleAttributeSet();
err = new SimpleAttributeSet();
...
System.setOut(new TextPanePrintStream(console, out));
System.setErr(new TextPanePrintStream(console, err));
```

## Live view

To provide a more direct feedback to the user and give him a feeling of where his entities are placed without going through the wait of a full render, a live view module was implemented. 

It consists mainly of two components: a `Scene` translator and an 60 frames per second OpenGL view.

The OpenGL view was implemented using jPCT, a library acting as a front-end for LWJGL, the most commonly used OpenGL library for Java. Given the scope of this project, a front-end was used to reduce development time: jPCT provides a complete framework with wrapper methods that do all the low-level OpenGL calls internally, enabling us to use simple methods like `Object3D.getCube(scale)` for creating a cube, instead of the following LWJGL code:

```{.java caption="Creating a cube with LWJGL"}
GL11.glBegin(GL11.GL_TRIANGLES);
GL11.glVertex3f( 0.0f, 1.0f, 0.0f);
GL11.glVertex3f(-1.0f,-1.0f, 1.0f);
GL11.glVertex3f( 1.0f,-1.0f, 1.0f); 
// Repeated 5 more times, 1 for each face
...
GL11.glEnd();
```

And that is without colour or texture management, which jPCT provides easy methods for. Another useful aspect is the easy integration in Swing windows, albeit with some workaround having to be used when adding multiple viewports.

The conversion is pretty straight forward: we recursively go through a `Scene` object and whenever a supported entity or light is encountered, the jPCT equivalent creator method is invoked, and the created object is translated to its correct position.

During that conversion process, some sacrifices have to be made. The CSG objects cannot be easily translated to the polygonal world and computing the positions of the vertices of a CSG operation would be a project of its own right. Also, limitations of jPCT only allow for one light source for use with shadow mapping, which will make the preview even more different than the resulting ray-traced render.

\customfig{img/liveview.png}{The live view and its 3 viewports}{}{}{}

Finishing and testing the live view module was a very exciting moment, because comparing the same `Scene` rendered once with our ray tracing engine and then with OpenGL would validate that all our algorithms are correct. And, indeed, all lines match, the perspective is correct.

\customfig{img/comparison.png}{Live view lines up with ray-traced result}{}{}{}

After tweaking the module for a moment, the lack of a background in the previewed scenes was quickly made apparent. Because there are no rays in rasterization, we cannot just assign a colour to rays hitting infinity like we did with ray tracing (see section \ref{subsec:bg}).

So instead, we invented a clever hack that uses jPCT's implementation of sky boxes^[In video games, sky boxes are used for backgrounds and consist of cube much larger in magnitude than the rendered world and 6 textures are mapped on its faces.]: during the conversion process of a `Scene`, a secondary empty `Scene` is created that shares the same background. In its settings, the field of view is set to \SI{90}{\degree}, which is the angle needed to see only one face of the cube when the camera is placed in its centre.

We then proceed to render 6 separate images of the panorama background and use them for the jPCT sky box, which leads to an almost similar result to the regular spherical projection mapping used earlier, with the only difference being that the field of view angle won't affect the visible section of the sky box, which will look the same regardless of the zoom.

To help the user place and aim the camera in his scene, the bottom-right live viewport supports mouse dragging to move the camera around its aiming point. The mouse wheel controls the FOV angle and provides a direct way to control what objects are visible in any shot.

Finally, a small render panel enables for a quick ray-traced preview of the current camera settings. The "Copy camera" button puts a script snippet containing the current camera settings in the user clipboard for him to put into his code.

## Docking Frames

In order to provide modularity to the IDE, we wanted to add a docking feature to all components of the user interface. After trying a few libraries, the simplicity and efficiency of Docking Frames^[http://dock.javaforge.com/] was enough convincing and we settled on that library.

\begin{figure*}
  \includegraphics[width=\textwidth, keepaspectratio]{img/layout.png}
  \caption{Default layout of the IDE}
\end{figure*}

\begin{figure*}
  \includegraphics[width=\textwidth, keepaspectratio]{img/userlayout.png}
  \caption{Layout modified by dragging components around}
\end{figure*}

Docking Frames works by defining "zones", contained in a `CContentArea` object. The default layout consists of a central main area, and several potential tool bar areas in each cardinal location.

The components that are placeable in the main zone are called `CDockable`. They have a title, an icon, and multiple Swing components can be placed inside, like a `JPanel` or `JFrame`, and appear to the user as a regular tab.

Finally, inside this main zone, a grid can be defined with weights for each dockable size. Multiple dockables can be placed in the same grid zone, which will stack them. The final result is a very modular layout where the user can drag any tab to put them in another grid zone, resize any zone, hide tabs, pin them, extract them to a floating window, etc.

A lot of time was spent on customizing the appearance of Docking Frame components. By default, the UI appears very crude and doesn't match with the rest of the application. But, where Docking Frames shines when it comes to ease of use, efficiently adding components to zones and robustness, it fails in regards to customization. 

Although it provides several themes, customizing them or creating a new one is not an easy task. Many tweaks had to be done to non-documented properties, which names and effects had to be found by reading the source code and experimenting a lot.

Thankfully, the project manager was very helpful and responded very quickly to all question we had on the forum during this process, and a look matching our theme was achieved.

## Themes

For the appearance of the user interface, we used the Substance API^[http://insubstantial.github.io/insubstantial/substance/], for several reasons.

In past project, a lot of time would be spent on perfectly aligning component just to find out that on another OS, everything looked different because of how the OS treats title bars, margins etc. 

Using the built-in "metal" Look & Feel would solve this problem, but it is very unpleasant on the eyes. Using a third-party L&F API guarantees that a window will look exactly the same on all platforms: the Substance L&F is good-looking, and provides multiple colour schemes for all tastes.

\customfig{img/darktheme.png}{The dark side of the application}{}{}{}

Similarly, the two fonts used for both the code editor and regular use are provided with the application, to ensure that everything will appear identical across platforms.

To use external fonts that are not installed on the user's system, it is possible to temporarily register them using the following code, which enables the use of any font in a regular fashion as if it was installed:

```{.java caption="Registering fonts"}
URI uri = getClass().getResource(path).toURI();
File fontFile = new File(uri);
Font font = Font.createFont(Font.TRUETYPE_FONT, fontFile);
GraphicsEnvironment ge =
    GraphicsEnvironment.getLocalGraphicsEnvironment();
ge.registerFont(font);
```

In order to satisfy most users we implemented two themes for our application: dark and light. A `Theme` singleton class contains all informations necessary for all the various components, the colour scheme and the L&F name. 

Finally, instead of manually changing all the icons to white using an external tool and wasting disk space, the icon colours are modified on the fly when the theme loads, using bit masks to invert colours while keeping the alpha channel intact:

```{.java caption="Programmatically inverting icon colours"}
BufferedImage bi = (BufferedImage) img;
for (int y = 0; y < bi.getHeight(); ++y) {
  for (int x = 0; x < bi.getWidth(); ++x) {
    int c = bi.getRGB(x, y);
    bi.setRGB(x, y,
      0x00ffffff - (c | 0xff000000) | (c & 0xff000000));
  }
}
```