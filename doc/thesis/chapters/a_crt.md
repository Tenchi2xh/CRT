# CRT

\customfig{../res/logo/logo_small.png}{The CRT logo}{, showing commonly ray-traced primitives and an old-fashioned cathode ray tube pixelated effect.}{}{}

This paper is about the development of **CRT**, a project done for the requirements of my Bachelor's degree at HEIG-VD, under the guidance of Prof. Pier \textsc{Donini}.

The goal of this project was to study, design and implement an artistic conception tool for creating realistic three-dimensional images, based on a physical simulation of light and its fundamental properties (diffusion, reflection and refraction), method known as *ray tracing*, along with designing a custom *scripting language* used to describe scenes, that will then be compiled then rendered. Additionally, a pleasant IDE-like user interface will be developed to create, edit and render ray-traced scenes.

**Ray tracing** is a technique for computer optical calculations used for rendering an image or for optical studies. This technique involves simulating the inverse path light takes *from* the camera and compute the interactions of light rays with abstract primitive objects, composing a scene. This technique is used in the entertainment industry by *Pixar* and *Dreamworks* for animation, and by 3D artists for concept art.

The project goals were designed to involve a lot of different computer science domains and general science fields: mathematics, physics, algorithms, language grammar, compilation, user interfaces and object-oriented design patterns.

## Technologies

CRT was realized in Java 8, with the help of the following open-source libraries:

- ANTLR4
- Apache Commons
- Docking Frames
- jPCT
- LWJGL
- Netbeans OpenIDE
- Substance

When applicable, they were integrated to the project by using *Maven*.

## Final report

This paper follows the previous intermediary report, released a month ago. It will relate all the knowledge I acquired regarding **ray tracing** during the development of CRT, a chapter about the **scripting language** I designed to describe scenes and compile them, and a chapter explaining the different elements included in the **user interface**.

The following sections contain:

- What was the introduction for the intermediary report (the state the project at that time and what remained to be done. 
- The current state of the project after a month of finishing touches.
- An overview of the companion archive file
- A quick manual

### Intermediary state

Here are all the features that had been implemented so far:

- The ray tracer can render some primitives and panorama backgrounds. It supports CSG^[See section \ref{sec:csg}] operations (albeit with some bugs), supersampling, depth of field blur, and parallelization.
- The user interface is mostly fully designed but not fully functional. It has a dockable windows system which displays a syntax-highlighted code editor, a property tree, a renderer panel, a toolbar, a console which redirects `stdout` and `stderr`, and some computer statistics graphs. The interface supports two themes: light and dark, and all the icons are inverted at runtime depending on the current theme.
- A live view module converts scene objects to OpenGL displayable elements, with multiple viewports and object picking.
- The language's grammar is almost fully complete.
- The compiler compiles everything the grammar defines so far. It features nestable scopes, recursive variable solving with references, and all the elements needed to describe a scene. It has custom error reporting functionalities and indicates precisely where the errors are.

The following feature were missing from the project state:

- The user interface lacks many features: file/project management, updating the property tree and updating the code from it, importing the settings tab from the prototype and giving it functionalities for grabbing settings from the code and writing back to it, the live view perspective, a status bar, a configuration window, an about window.
- The live view has no editing capabilities.
- The grammar lacks rules for loops and conditions, and the CSG blocks are wrong.
- No animation support for now.
- The ray tracer lacks: gamma support, more primitives, affine transformations, procedural texturing, post processing.

### Progress since intermediary report

Not everything could be done in the remaining month after the intermediary report. However, the application is in a very satisfactory state and can produce high quality artistic images (see figures \ref{fig:ex1} and \ref{fig:ex2} in the next pages).

The goals stated in the first section were reached, and a lot was learned throughout the year about ray tracing mechanics, language design and compilation, and user interface design.

Here is the list of features that were stated missing in the last report:

- The user interface is fully functional: the user can open and save projects, export pictures, switch the representation mode, switch to full screen, see the "about" dialog
- The live view has been integrated into the user interface and can now preview any script. It can move the camera around and change the field of view. The camera can be exported to the script for use.
- The animation mode has been completed.

### What is packaged

This report comes shipped along with a compressed archive containing:

- A compiled version of CRT
- A folder containing a few examples
- A resource folder with 16 panoramic backgrounds
- The CRT source code
- The source code for this paper

### Quick manual

To launch the application, use one of the provided shortcuts:

- `CRT_Dark.sh` or `CRT_Dark.bat` for the dark theme
- `CRT_Light.sh` or `CRT_Light.bat` for the light theme

The way to use the application is quite straight-forward: the user can write code in the editor, compile, and see the result.

To learn the CRT language syntax, many examples can be found in the `demos/` folder: use the "Open" button in the toolbar, or `ALT+O`. Some of these examples can take a long time to render --- if they do, the user can cancel the rendering and reduce the quality settings:

- Set the `supersampling` to 1 in the `Settings` block
- Set the `dofsamples` value to a lower value in the `Settings` block
- Or use the live view module (see below)

A few useful keyboard shortcuts: 

- `ALT+R` compiles and renders the current code, or stops the render if it is in progress
- `ALT+S` saves the current code
- `ALT+O` opens a saved project
- `ALT+E` exports the current render to a PNG file
- `ALT+P` refreshes the live view module
- `CTRL+M` extends any extendable tab

When valid code is present in the editor, press the eye button in the toolbar to compile and refresh the live view, which will provide you with a free camera that you can *drag* around using the mouse. You can also use the mouse wheel to change the field of view, and use the preview feature to obtain a small ray-traced render as seen from the current camera.

When a satisfactory point of view has been found, the "Copy camera" button will put the current camera in the clipboard for you to paste in the appropriate section of the script.

To produce animations, the variable named `t` can be used in the script to represent time (see section \ref{sec:anim}). Then, in the "Animation" tab, choose the desired amount of frames to render, and press the "Render" button. When finished, a GIF file will be saved in the current directory, and by using the slider, the animation can be previewed at will.

Finally, here are a few useful functions not found in the examples: `sin(t)`, `cos(t)`, `tan(t)`, `rand(min, max)`.

\onecolumn
\thispagestyle{empty}
\begin{landscape}
\begin{figure*}
  \centering
  \hspace*{-0.05\hsize}
  \includegraphics[width=1.1\hsize,keepaspectratio]{img/drops.png}
  \caption[Example of a CRT render]{Example of a CRT render -- this render took 42 minutes for CRT to render and shows the potential of ray tracing.}
  \label{fig:ex1}
\end{figure*}
\end{landscape}

\thispagestyle{empty}
\begin{figure}
  \includegraphics[width=\hsize,keepaspectratio]{img/poster1.png}
  \caption[Second example of a CRT render]{Second example of a CRT render -- this intricate sculpture was realized thanks to CSG operations. This picture will be used for the promotional poster (credits to prof. Alfred \textsc{Flückiger} for providing the panorama of the school).}
  \label{fig:ex2}
\end{figure}

\twocolumn
