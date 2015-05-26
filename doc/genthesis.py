#!/usr/bin/python

import os, glob

fn = "thesis.md"

def add(s): os.system('echo "' + s + '" >> ' + fn)

def cat(f): os.system('cat ' + f + ' >> ' + fn)

def np(): add()

os.system('> ' + fn)

print "Concatenating files..."

add("""\\onecolumn
\\chapter*{Foreword}
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

os.system('pandoc thesis.md -o thesis.tex --toc \
          --template="templates/thesis.tex" -N --listings \
          -V lang=english -V geometry:hmargin=1.5cm -V geometry:vmargin=2.3cm \
          -V urlcolor=blue -V linkcolor=black --latex-engine=xelatex --chapters \
          --bibliography=bib/bibliography.bib --biblatex')
os.system('xelatex thesis')
os.system('biber thesis')
os.system('xelatex thesis')

toRm = ["tex", "md", "aux", "bcf", "lof", "log", "lol", "lot", "out", "run.xml", "toc", "synctex.gz", "bbl", "blg"]
toRm = ["thesis." + s for s in toRm]
os.system('rm -rf ' + " ".join(toRm))
os.system('mv thesis.pdf pdf')