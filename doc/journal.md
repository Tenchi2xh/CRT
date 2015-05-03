% CRT - Development Journal
% Hamza Haiken <tenchi@team2xh.net>
  Prof. Pier Donini (pier.donini@heig-vd.ch)
% B.A. Thesis, HEIG-VD 2014-2015

\newpage

# 09.03.2015 - 15.03.2015

- GitHub repository^[https://github.com/Tenchi2xh/CRT] created
    - `.gitignore` file tailored for project
- Specifications document
- Maven project created via NetBeans
    - Some dependencies
    - Default license header set (GPL3)
- Logo design
    - First simple attempt
    - Created CRT effect as a Photoshop action and patterns
    - Multiple sizes

# 16.03.2015 - 22.03.2015

- \LaTeX thesis template
- Python script for building thesis
- UML class diagrams for rendering process, ANTLR4 structure
- Some sequence diagrams
- Paragraphs on the rendering process and grammar parsing
- Reworked the grammar

# 23.03.2015 - 30.03.2015

- Established basic ray tracing skeleton
- Refactored code from old prototype
- Changed structure according to new decisions made with supervisor
- Added Box primitive

# 01.04.2015 - 07.04.2015

- Work on CSG operations:
    + Union OK
    + Intersection OK
    + Difference has buggy normals but works
- Change in `Hit` class model to indicate entry and exit points of `Ray`
- Corrections to Sphere primitive intersection and normals
- Rendering classes refactoring
- Rendering UML diagram split in two, added cardinalities and fixed a few aspects

# 08.04.2015 - 15.04.2015

- Wrote the grammar from scratch
- Wrote test cases
- Started work on compiler:
    - General framework
    - Scope and variables
    - Scoped variable resolution
    - Variable reference pointers
    - Literal variable assignments
    - List variable assignments
    - List access
    - Function calls for color and vector types

# 16.04.2015 - 22.04.2015

- Corrections
- Typography improvements
- Section about ANTLR, the grammar, BNF listing and operators table
- Section about ray tracing history, workings, process
- Citing image sources
- Rewrote how the camera system works
    + Removed awkward $4 \times 4$ matrix system (more appropriate for rasterisation)
    + System with up and right vectors
    + Left-handed coordinate system
    + Corrected camera projections
- Rewrote how lights the work
    + Multiple light types
    + Each light type has to give its own direction vector and distance. This lead to correct a bug where a light source is inside a primitive, because the ray intersection point is exactly on the surface, it tries to go through anyway instead of stopping
- Much work on CSG
- Animation test
- Drew diagrams with TikZ for light explanations
- Experimented with global illumination
- Adapted depth-of-field calculations to new camera system

# 23.04.2015 - 29.04.2015

- Set up base environment for jPCT + LWJGL in Maven
    + Local repository
    + JVM arguments
- Configured Maven's POM for producing distributable content
- Corrected run classpath

- Some documentation
- Idea for poster

- Set up base test for jPCT loop
- Basic Scene to jPCT converter
- Ported lighting system with basic shadow mapping for first parallel light
- Many scenes from close up would clip and setting a closer near plane would break shadows. So a hack where all distances and values like light falloff are multiplied by a constant when imported and everything is bigger, making the default nearPlane appear small in comparison
- Project background on a cube via ray tracing to export to jPCT skybox
- Corrected ray tracing camera for looking straight up or down
- Live view camera control, + mouse
- Implemented object picking in live view
- Optimized rendering speed
- Shader prototype

# 30.04.2015 - 06.05.2015

- Studied Docking Frames API
- Java Beans infos and editors
- Code editor with syntax highlighting and some features
- Entity trees
- Entity properties
- Docking system
- Language touch-ups
- Console panel, catching `stdout` and `stderr`, autoscrolling
- Renderer panel
- Dynamic icon system dependent on theme
- Theme singleton