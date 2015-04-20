\onecolumn
\section*{Foreword}
\addcontentsline{toc}{chapter}{Foreword}
Foreword...\twocolumn
\pagenumbering{arabic}


\newpage

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

### Camera

A lit and populated scene still needs a window through which we will observe it: the `Camera` class defines the point of view of our rendered scene.

It has a **position** and a **direction** vector, as well as a **field of view** angle.

The **field of view** of a camera *how much* it sees from left to right, or from top to bottom of the image (in photography, this would represent the focal length of the objective). In CRT, the field of view is defined vertically, as an angle in radians. Varying this parameter has a *zoom* effect when lowered, while a big value makes more things visible on the screen.

To further add to the user's creative possibilities, several artistic features which aim to mimic real-life cameras were implemented:

- Depth of field (DOF), effect that creates a plane in which objects are sharp, and blurry outside, akin to a tilt-shift effect in photography.
- An aperture shape, which will be used to physically simulate the shape that *bokeh* will have (see figure \ref{fig:bokeh}).
- A focal distance, defining at which distance objects are sharp.

\customfig{img/bokeh.jpg}{Real-life \emph{bokeh}}{: the blurriness of out-of-focus objects will take the shape of the camera's aperture (pinhole). Here, the \emph{bokeh} is octogonal.}{bokeh}{Scott Tucker on Flickr}

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

\customfigB{img/rasterisation.png}{Rasterisation of a triangle}{: once the vertices have been projected on the screen, a discrete pixel is ``lit'' if its continuous centre is contained within the projected triangle's boundaries.}{}{Wikipedia}

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

### Backward tracing implementation

For every pixel on the screen, a ray is generated, then traced. Because each pixel's tracing is **independent** from one another, the process can be parallelized. This is easily done thanks to the new Java 8 API and its *streams*: after splitting the screen into blocks in a Java list `coords`, all we have to do is call:

```{.java caption="Java 8's easy parallelization"}
coords.parallelStream().forEach(
    (int[] c) -> processPixel(c, image, scene));
```

The process of tracing a ray takes the following steps:

1. Start with a black colour
2. Search for the entity closest to the ray's origin that intersects with the ray
3. For every light in the scene, send a **shadow ray** originated on the intersection point towards the light
    - Add the *ambient* factor of the light
    - If it hits nothing before reaching the light, add a mix of the light's colour and the object's to the current colour. The light's colour contribution is attenuated by two factors:
        * The less parallel the surface normal is with the incoming ray, the darker
        * The further the light had to travel, the darker (inverse square law) 
    + If the shadow ray hits an object, it is in its shadow: no colour is added
4. If the surface's material has a reflective component, recursively trace a **reflection ray** in an angle symmetrical to the angle of incidence, and add the resulting colour 
5. If the surface's material has a refractive component, recursively trace a **refraction ray** in an angle obtained with the Snell-Descartes law, and add the resulting colour

### Coordinate system

\begin{figure}
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

### Ray generation

Ray:

$$ \vec{o} + t\vec{r} $$

### Primitives

Sphere:
Ray-sphere intersection:

- Diagram


### Constructive solid geometry

\customfigC{img/csg.png}{A piano foot obtained from CSG operations}{}{csgexample}{}

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


\newpage

# Language

So far, we can compose and render scenes directly by writing them in Java by instantiating `Scene` and `Entity` objects. But for the user to be able to *compose* his own scenes inside a design environment, we need to define a language: the **CRT scripting language**.

The CRT scripting language follows an *imperative* paradigm and aims to be simple yet permissive enough to enable creativity.

It features two block types for describing a scene and its settings, variables that can store entities, literal values, and point to other variables, parametric procedures (hereinafter referred to as "*macros*") with nested scopes but no return value, and entity modifiers for affine transformations.

Visually as well as syntactically, the language tries to be simple on the eyes, with no end-of-statement terminator. Here is a sample of what it looks like:

```{.haskell caption="Sample CRT script"}
--Entities-------------------------------------------------
sphere1 = Sphere {
    center -> vec3(0, 0.5, 0)
    radius -> 0.5
}

--Constants------------------------------------------------
n = 18
max = (3 * n) / 4 + 5

--Macros---------------------------------------------------
myMacro = Macro (arg1) {
    i = 0
    -- Draw sphere1 "max" times on the x axis
    while (i < max) {
        sphere1 <translate vec3(i*1.0, 0.0, 0.0)>
        i = i - 1
    }
}
```

## ANTLR

The language's grammar will be designed in a EBNF variant, the G4 syntax from **ANTLR**^[**AN**other **T**ool for **L**anguage **R**ecognition], a Java parser generator.

ANTLR will use that grammar specification to automatically generate the code of a lexer, a parser, and base classes useful for implementing tree traversal using design patterns such as *listeners* and *visitors*.

\customfig{uml/antlr.eps}{Family of classes generated by ANTLR4}{}{antlrclass}{}

ANTLR works by first lexing the code into *tokens*, defined by their types in the grammar (e.g. names, identifiers, symbols, etc.) then parsing those tokens using the grammar *rules*, producing a parse tree where all the leaf nodes are tokens. 

\customfig{img/antlr-process.png}{Language recognition process}{.}{antlrprocess}{ANTLR}

For our compiler, we will use the *visitor* pattern, which allows for more control over the tree traversal; the listener provided by ANTLR automatically traverses the tree whereas the visitor forces manual traversal implementation.

Using the generated lexer and parser, we can produce a parse tree (lines 3-6). Then, using custom-made visitors, we can visit each node of the tree to compile the code to a `Script` object (line 8):

```{.java caption="Generating a parse tree and compiling" }
String code = "..."

CRTLexer lexer = new CRTLexer(new ANTLRInputStream(code));
CommonTokenStream tokens = new CommonTokenStream(lexer);
CRTParser parser = new CRTParser(tokens);
ParseTree tree = parser.script();

Compiler compiler = new Compiler(code);
Script script = compiler.visit(tree);
```

## Grammar

The designed grammar is non-ambiguous (context-free), but uses **left-recursion**^[http://en.wikipedia.org/wiki/Left_recursion] for ease of writing *and* reading, which ANTLR supports since version 4.2.

Some parts were inspired from example grammars provided by the ANTLR team on GitHub, in particular the Java grammar^[http://github.com/antlr/grammars-v4/blob/master/java/Java.g4], from which much was learned about left-recursion and operator precedence. 

Furthermore, the "ANTLR 4 IDE" *Eclipse* plug-in^[http://github.com/jknack/antlr4ide] proved to be very useful during the development of the grammar. It provides useful tools for debugging such as syntax diagrams and a live parse tree visualisation --- just by selecting a grammar rule and typing in code, a corresponding parse tree is updated at every keystroke.<!-- Figure \ref{fig:parsetree} shows such a generated parse tree.

\customfig{img/parse-tree.png}{ANTLR 4 IDE's live parse tree}{, displaying a macro assignment}{parsetree}{} -->

A similar (and official) plug-in also exists for *NetBeans*, the main IDE used during the development of this project, however it was not compatible with the latest versions of NetBeans.

Because it is important to make a separation between parsing and compiling, the grammar contains no special verifications; they are done at compile time. This makes the grammar *much* more readable and easy to understand.

Also, ANTLR provides a feature for **labelling** the *alternatives* of a rule, which it will use for code generation where it will generate one visitor method per label (e.g. instead of having to implement a very extensive `visitExpression()` method, it will be broken down to all its alternatives `visitAddition()`, `visitMultiplication()` etc.).

### Rules

This section lists all the grammar rules defined in the `CRT.g4` file, in a **BNF** notation, followed by a quick overview of how they work.

Nonterminal names are enclosed within angled brackets\ ($\langle ... \rangle$). Names starting with a capital are rules, small letter are token types.

\begin{numberedgrammar}
<Script>        ::= <Statement>*

<Statement>     ::= (<Settings> | <Scene> | <Expr>)

<Settings>      ::= \sv{Settings} \sv{\{} <Attribute>* \sv{\}}

<Scene>         ::= \sv{Scene} \sv{\{} <Expr>* \sv{\}}

<Expr>          ::= <Primary>
               \alt <Object>
               \alt <Macro>
               \alt \sv{[} <ExpressionList>? \sv{]}
               \alt <Expr> \sv{[} <Expr> \sv{]}
               \alt <Expr> \sv{(} <ExprList>? \sv{)}
               \alt <Expr> \sv{\textless} <Modifier> (\sv{,} <Modifier>)* \sv{\textgreater}
               \alt (\sv{+} | \sv{-}) <Expr>
               \alt \sv{!} <Expr>
               \alt <Expr> (\sv{*} | \sv{/} | \sv{\%}) <Expr>
               \alt <Expr> (\sv{+} | \sv{-} | \sv{\textasciicircum}) <Expr>
               \alt <Expr> (\sv{\textless=} | \sv{\textgreater=} | \sv{\textless} | \sv{\textgreater} | \sv{==} | \sv{!=}) <Expr>
               \alt <Expr> \sv{\&\&} <Expr>
               \alt <Expr> \sv{||} <Expr>
               \alt <Expr> \sv{?} <Expr> \sv{:} <Expr>
               \alt <Expr> \sv{=} <Expr>

<ExprList>      ::= <Expr> (\sv{,} <Expr>)*

<Primary>       ::= \sv{(} <Expr> \sv{)}
               \alt <Literal>
               \alt <identifier>

<Object>        ::= <name> \sv{\{} <Attribute>* \sv{\}}

<Macro>         ::= \sv{Macro} \sv{(} <ParamList>? \sv{)} \sv{\{} <Expr>* \sv{\}}

<ParamList>     ::= <identifier> (\sv{,} <identifier>)*

<Literal>       ::= (<integer> | <float> | <string> | <boolean>)

<Attribute>     ::= <identifier> \sv{->} <Expr>

<Modifier>      ::= \sv{scale} <Expr>
               \alt \sv{translate} <Expr>
               \alt \sv{rotate} <Expr>
\end{numberedgrammar}

A **script**\ (1) is a set of **statements**\ (2), which can either be settings blocks, scene blocks, or expressions.

**Settings** and **scene** blocks\ (3, 4) are expressed using their names followed by braces containing either a number of attributes, or expressions --- this difference existing because settings have defined names to which we can assign values, and a scene renders all contained expressions that resolve to an entity (see section \ref{subsec:entities}).

An **expression** is either a primary type\ (5), an object\ (6), a macro\ (7), or one of the following:

(8)  List of *heterogeneous* expressions\ (21)
(9)  Access list element
(10) Macro call, which takes an optional list of expressions\ (21) as formal parameters
(11) Entity modified with an affine transformation
(12) Sign unary operators
(13) Negation boolean unary operator
(14) Multiplication, division and modulo operators
(15) Addition and subtraction operators. If both operands are entities, the operators are instead the CSG union (`+`), difference (`-`) and intersection (\texttt{\textasciicircum})
(16) Boolean comparison operators
(17) Boolean conjunction operator
(18) Boolean disjunction operator
(19) Ternary operator
(20) Assignment operator

A **primary** type is either a parenthesised expression (22), a literal type (23) or an identifier (24) --- a token made of alphabetical characters starting with a small letter.

An **object** (25) has a name --- a token made of alphabetical characters starting with a capital letter --- and is followed by a brace separated block of attributes.

A **macro** (26) starts with the word `Macro` and a list of formal parameters (27), followed by a brace separated block of expressions.

A **literal** type (28) can be one of four token types:

- A whole number
- A decimal number
- A string of characters inside straight double quotes
- A boolean value (the words `true` or `false`)

**Attributes** (29) are identifier tokens followed by an arrow (`->`) and an expression.

Finally, **modifiers** (which apply an affine transformation to an entity) can either be a scaling operation (30), a translation (31) or a rotation (32).

Without ANTLR's compatibility with left-recursion, most of the rules referencing expressions would have to be written in such a way that the grammar is only read from left to right, involving a *lot* more rules.

### Operators

Because we used *left-recursion* to write the grammar, the operator precedence is visually clear at first sight --- however, for the sake of completeness, table \ref{tab:operators} shows all operators, their level of precedence (lower level is higher precedence), and a short description.

\begin{table}[h]
\renewcommand{\arraystretch}{1.2}
\begin{tabular}{rclc}
\toprule
Level              & Operator                       & Description                  & Associativity                  \\\midrule
1                  & \texttt{{[}{]}}                & List access                  & left-to-right                  \\
2                  & \texttt{()}                    & Macro call                   & left-to-right                  \\
3                  & \texttt{\textless\textgreater} & Entity modifier              & left-to-right                  \\\midrule
\multirow{2}{*}{4} & \texttt{+}                     & Unary plus                   & \multirow{2}{*}{right-to-left} \\
                   & \texttt{-}                     & Unary minus                  &                                \\\midrule
5                  & \texttt{!}                     & Boolean NOT                  & left-to-right                  \\\midrule
\multirow{3}{*}{6} & \texttt{*}                     & Multiplication               & \multirow{3}{*}{left-to-right} \\
                   & \texttt{/}                     & Division                     &                                \\
                   & \texttt{\%}                    & Modulo                       &                                \\\midrule
\multirow{5}{*}{7} & \texttt{+}                     & Addition                     & \multirow{3}{*}{left-to-right} \\
                   &                                & (CSG Union)                  &                                \\
                   & \texttt{-}                     & Subtraction                  &                                \\
                   &                                & (CSG Difference)             &                                \\
                   & \texttt{\textasciicircum }     & CSG Intersection             &                                \\\midrule
\multirow{6}{*}{8} & \texttt{\textless=}            & Less than or equal           & \multirow{6}{*}{left-to-right} \\
                   & \texttt{\textgreater=}         & More than or equal           &                                \\
                   & \texttt{\textless}             & Less than                    &                                \\
                   & \texttt{\textgreater}          & More than                    &                                \\
                   & \texttt{==}                    & Equals                       &                                \\
                   & \texttt{!=}                    & Not equal                    &                                \\\midrule
9                  & \texttt{\&\&}                  & Boolean AND                  & left-to-right                  \\
10                 & \texttt{||}                    & Boolean OR                   & left-to-right                  \\
11                 & \texttt{?:}                    & Ternary operator             & right-to-left                  \\
12                 & \texttt{=}                     & Assignment                   & right-to-left                  \\

\bottomrule
\end{tabular}
\caption{List of CRT operators}
\label{tab:operators}
\end{table}

## Compiling process




\onecolumn
\setcounter{chapter}{0}
\renewcommand\thechapter{\Alph{chapter}}
\chapter{Acknowledgements}
Acknowledgements...


\chapter{Appendix}
## Mathematical helper classes

- `Matrix4`
- `Vector3`
- Poisson disk distribution
    - Explanation
    - Nice diagrams
    - Explain why it's slow and not so useful
- Uniform Distribution
    - Explanation
    - Nice diagrams
    - Explain why it's nice
    - Credits to J.-F. Hêche

## GUI

- Substance
- `GUIToolkit`


\chapter{Bibliography}
Bibliography...