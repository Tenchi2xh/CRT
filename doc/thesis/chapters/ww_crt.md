# CRT

This paper is about the development of **CRT**, a project done for the requirements of my Bachelor's degree at HEIG-VD, under the guidance of Prof. Pier \textsc{Donini}.

The goal of this project was to study, design and implement an artistic conception tool for creating realistic three-dimensional images, based on a physical simulation of light and its fundamental properties (diffusion, reflection and refraction), method known as *ray tracing*, along with a custom *scripting language* used to describe scenes, that will be compiled then rendered.

Ray tracing is a technique for computer optical calculations used for rendering an image or for optical studies. This technique involves simulating the inverse path light takes from the camera and compute the interactions of light rays with abstract primitive objects, composing a scene. This technique is used in the entertainment industry by Pixar and Dreamworks for animation, and by 3d artists for concept art.

The project was designed to involve a lot of different computer science and general science fields: mathematics, physics, algorithmics, grammar, compilation, user interfaces and object-oriented design patterns.

## Workflow

## Technologies

## Intermediary report

This version of the paper is an **intermediary report**: it contains every section that has been written so far, as well as this section which will describe the current state of affairs and what remains to be done.

### State of affairs

Here are all the features that have been implemented so far:

- The ray tracer can render some primitives and panorama backgrounds. It supports CSG^[See section \ref{sec:csg}] operations (albeit with some bugs), supersampling, depth of field blur, and parallelization.
- The user interface is mostly fully designed but not fully functional. It has a dockable windows system which displays a syntax-highlighted code editor, a property tree, a renderer panel, a toolbar, a console which redirects `stdout` and `stderr`, and some computer statistics graphs. The interface supports two themes: light and dark, and all the icons are inverted at runtime depending on the current theme.
- A live view module converts scene objects to OpenGL displayable elements, with multiple viewports and object picking.
- The language's grammar is almost fully complete.
- The compiler compiles everything the grammar defines so far. It features nestable scopes, recursive variable solving with references, and all the elements needed to describe a scene. It has custom error reporting functionalities and indicates precisely where the errors are.

### Remaining features

- The user interface lacks many features: file/project management, updating the property tree and updating the code from it, importing the settings tab from the prototype and giving it functionalities for grabbing settings from the code and writing back to it, the live view perspective, a status bar, a configuration window, an about window.
- The live view has no editing capabilities.
- The grammar lacks rules for loops and conditions, and the CSG blocks are wrong.
- No animation support for now.
- The ray tracer lacks: gamma support, more primitives, affine transformations, procedural texturing, post processing.

### Re-evaluation of goals