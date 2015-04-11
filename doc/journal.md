% CRT - Development Journal
% Hamza Haiken (hamza.haiken@heig-vd.ch)
  Prof. Pier Donini (pier.donini@heig-vd.ch)
% B.A. Thesis, HEIG-VD 2014-2015

\newline

# 09.03.2015 - 15.03.2015

- GitHub repository[^https://github.com/Tenchi2xh/CRT] created
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
- Wrote a test case
- Began work on compiler