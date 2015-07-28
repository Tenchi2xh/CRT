\onecolumn

# Conclusion

CRT is a success: the application is fully usable, and with only creativity as a limit, great results can be achieved using it. It completed all the major goals that were set at the beginning of the project:

- Ray tracing mechanics were thoroughly studied and experimented with. A complete ray tracing engine was implemented in an object-oriented fashion
- The final scripting language is very pleasing to use and makes experimenting with the engine very easy
- The user interface is pleasing to look at and use

The source code line count reached more than 17000, which makes CRT the biggest solo project I worked on. Almost half of the development time was spent on the ray tracing engine itself, experimenting, correcting its bugs. However, there would be a lot more I would have wanted present in the project, given more time:

- Global illumination, which in universities is the subject of a complete one-semester course dedicated to only the complex mathematical concepts behind it
- More primitives, which take some time to implement and would have prevented me from achieving all other goals
- Procedural texturing
- Post processing

The live view experiment turned to be a successful one, and I thoroughly used its advantages during the creation of the demonstration scenes, as well as during the development of the project itself, for debugging purposes. It was very gratifying, once the module worked, to see that all the lines and perspective lined up perfectly with the home-made calculations of the ray tracing engine, effectively validating all my work through a third party engine like OpenGL. My only regrets is not having the time to develop an editor around it, for users who wish to compose a scene only using their mouse and the live view. The idea of a live view on itself is very unique to the project, and doesn't exist even in very popular scripting based ray tracers like POV-Ray.

An additional goal was added late during the project development, following the idea of my supervisor, prof. Pier \textsc{Donini}: animations. The idea turned out to be very appropriate for the project, following the footsteps of Pixar. Creating and watching ray-traced animations is very entertaining, and adds a whole new dimension of creativity. For now, the application can only output simple GIF files for rendered animations, and creating movie files in Java without the use of external platform-specific tools seems unfortunately unavoidable.

A considerable amount of time was spent documenting the project by way of this paper and designing the diverse explanatory diagrams. Using \LaTeX proved to be a trial of patience, but was its own reward. A point of honour was given to the typesetting and design of this document, and I sincerely hope it was an enjoyable read.

Finally, achieving a project which goals I set myself was very satisfying. I learnt a lot along the way, and at every step, seeing results render before my eyes was exhilarating.