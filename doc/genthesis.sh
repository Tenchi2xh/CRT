pandoc thesis.md -o pdf/thesis.pdf --toc \
--template="templates/thesis.tex" -N \
-V lang=english -V geometry:hmargin=2.3cm -V geometry:vmargin=2.3cm \
-V urlcolor=blue -V linkcolor=black --latex-engine=xelatex
rm -rf tex2pdf.*
