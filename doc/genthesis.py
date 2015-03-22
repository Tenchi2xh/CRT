#!/usr/bin/python

import os, glob

fn = "thesis.md"

def add(s): os.system('echo "' + s + '" >> ' + fn)

def cat(f): os.system('cat ' + f + ' >> ' + fn)

def np(): add()

os.system('> ' + fn)

print "Concatenating files..."

add("""\\onecolumn
\\section*{Foreword}
\\addcontentsline{toc}{chapter}{Foreword}""")
cat("thesis/foreword.md")

add("""\\twocolumn
\pagenumbering{arabic}""")

chapters = glob.glob("thesis/chapters/*.md")
for c in chapters:
    add("\n\n\\newpage\n")
    cat(c)

add("""\n\n
\\onecolumn
\\setcounter{chapter}{0}
\\renewcommand\\thechapter{\\Alph{chapter}}
\\chapter{Acknowledgements}""")
cat("thesis/acknowledgements.md")

add("""\n\n
\\chapter{Appendix}""")
cat("thesis/appendix.md")

add("""\n\n
\\chapter{Bibliography}""")
cat("thesis/bibliography.md")

print "Building PDF..."

os.system('pandoc thesis.md -o pdf/thesis.pdf --toc \
          --template="templates/thesis.tex" -N \
          -V lang=english -V geometry:hmargin=1.5cm -V geometry:vmargin=2.3cm \
          -V urlcolor=blue -V linkcolor=black --latex-engine=xelatex --chapters')

os.system('rm -rf tex2pdf.*')
