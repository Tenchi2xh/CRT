                                       
                                               ,,-'"""'-,,                
                                             "'--.,_ _,.--'"              
          ,ad88ba,  88888888ba 888888888888  |      |      |              
         d8"'  `"8b 88      "8b     88       |      |      |   ^          
        d8'         88      ,8P     88       |      |      |  / \*.       
        88          88aaaaaa8P'     88        "'--.,.X+. ."  /   \**.     
        88          88""""88'       88          .Xx+-.    './     \***|   
        Y8,         88    `8b       88          XXx++-..    |      \**|   
         Y8a.  .a8P 88     `8b      88          XXxx++--..  |       \*|   
          `"Y88Y"'  88      `8b     88          `XXXxx+++--'_________\|   
                                                  `XXXxxx'                
                                                    '""'                   
________________________________________________________________________________

> CRT - Cathode Ray Tracer

Author: Hamza Haiken <tenchi@team2xh.net>
Source: https://github.com/Tenchi2xh/CRT

Quick start . . . . . . . . . . . . . . . . . . . . . . . . . . . . . . line 26
CRT language primer . . . . . . . . . . . . . . . . . . . . . . . . . . line 47
Keyboard shortcuts  . . . . . . . . . . . . . . . . . . . . . . . . . . line 222
________________________________________________________________________________

> Quick start

- Scripts from the "demos/" folder are commentated and will show the basic use
  of the CRT language.

- The live view gets refresh upon opening a script, and can be refreshed any
  time using the eye button in the toolbar (ALT+P).

- The bottom right live view viewport's camera can be controlled by dragging
  the mouse, and with the mousewheel. The camera currently shown will be used
  for the ray-traced preview, accessed by clicking the preview button. The
  copy camera button will put the current camera in code form in the clipboard,
  which then can be used in the script.

- To create animations, use the variable "t" for time in the script (see the
  two animation demos), choose the number of frames in the spinner on the left
  and click render. Once the render is complete, the animation can be viewed
  by dragging the slider, and an animated GIF file will be saved in the
  application's directory
________________________________________________________________________________

> CRT language primer

- Variables can be defined with an equal sign, and the usual
  mathematical operations are supported:

      a = (2 * 3) / sin(4.5)
      b = a*a
      c = rand(0.0, 0.5)
      l = [a, b, c]
      d = l[0] + l[1] * l[2] 

- Comments begin with a double dash

      -- This line is ignored by the compiler

- Supported entities are Box, Sphere and Plane

      box1 = Box {
          cornerA  -> vec3(-0.5, -0.5, -0.5)
          cornerB  -> vec3(+0.5, +0.5, +0.5)
          material -> myMat
      }

      sph1 = Sphere {
          center   -> vec3(0.0, 0.0, 0.0)
          radius   -> 0.5
          material -> myMat
      }

      ground = Plane {
          normal   ->  vec3(0.0, 1.0, 0.0)
          position -> vec3(0.0, 0.0, 0.0)
          material -> myMat
      } 

- Materials define how entities look

      myMat = Material {
          color -> rgb(0.5, 0.0, 0.5)
          reflectivity -> 0.5 -- Optional attribute
      }

- CSG operations are supported for entities

      hollowCube = box1 - sph1
      dice = box1 ^ sph1
      fatCube = box1 + sph1

- A CRT scene must contain a Settings block

      Settings {
          camera -> myCam
          lights -> [light1, light2]
      }

  Settings have the following optional fields

      title          -> "Scene title"
      author         -> "Scene author"
      date           -> "Creation date"
      notes          -> "Notes"
      background     -> myBackground
      supersampling  -> 2
      dofsamples     -> 32
      recursiondepth -> 4

  A supersampling value of 2 will produce an anti-aliasing render
  with a kernel of 4x4 pixels

  The value of dofsamples controls how many rays are traced per pixel
  for the depth of field blur (camera has to have an aperture value)

  The recursiondepth value controls how many times rays can bounce
  on reflective surfaces

- Cameras are defined with a block

      myCam = Camera {
          position -> vec3(0.5, 0.5, -0.3)
          pointing -> vec3(0.0, 0.5, 1.0)
          fov      -> 90.0

          focaldistance -> 1.6       -- Optional
          aperture      -> 40.0      -- Optional
          apertureshape -> "uniform" -- Optional
      } 

  The focaldistance sets the distance at which objects are in focus when
  dofsamples is more than 1

  The aperture value defines how blurry out-of-focus objects are
  (the wider, the blurrier)

  And the apertureshape defines the shape of the bokeh, and can be one of:
    - Uniform (faster than square)
    - Gaussian (gaussian distribution)
    - Triangle
    - Pentagon
    - Hexagon
    - Circle
    - Square
    - Line (very thin horizontal line)

- Lights can be of two types:

      light1 = ParallelLight {
          from     -> vec3(-0.2, 1.0, -0.2)
          pointing -> vec3(0.0, 0.0, 0.0)
          color    -> rgb(1.0, 1.0, 1.0)
          ambient  -> 0.0 -- Optional
      }

      light2 = PointLight {
          origin  -> vec3(0.0, 1.0, 2.0)
          color   -> rgb(0.2, 0.2, 0.9)
          falloff -> 15.0 -- Optional
          ambient -> 0.2  -- Optional
      }

  With a parallel light source, all light rays are parallel as if the
  source was infinitely away, and has no falloff

  A point light source dissipates with distance and produces divergent shadows

- A background can be of three types:

      solidBg = Background {
         color -> rgb(1.0, 0.0, 0.0)
      }

      gradientBg = Background {
          horizon -> rgb(1.0, 0.0, 0.0)
          sky     -> rgb(0.0, 0.0, 1.0)
      }

      imageBg = Background {
          image -> "/images/panorama/sky.jpg"
          angle -> 1.4 -- Optional
      }

  Images are searched in the "resources/" folder

- All block types that were defined above don't have to be stored in a variable
  and can be directly typed in a Settings or Scene block:

      Settings {
          ...
          background -> Background {
              ...
          }
      }

- Finally, all CRT scripts must contain a Scene block

  Scene {
      box1 - sph1
      Plane {
          ...
      }
  }

  Everything declared inside the Scene block will be rendered

- Built-in functions are

      sin(value)
      cos(value)
      tan(value)
      rand(lower, upper)

  The random seed is the same at each render to provide some consistency

- For concrete examples, please study the included demos in the "demos/" folder
________________________________________________________________________________

> Keyboard shortcuts

Toolbar     ALT+N         New CRT script
            ALT+O         Open CRT script
            ALT+S         Save current script

            ALT+R         Render current script
            ALT+P         Refresh the live view tab
            ALT+E         Save current render as PNG

            ALT+F         Toggle fullscreen

            ALT+A         Show the about window

Misc        CTRL+M        Expand active tab
            CTRL+SHIFT+E  Quick switch to tab
________________________________________________________________________________

Copyright (c) 2015 Hamza Haiken <tenchi@team2xh.net>

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
