gendoc () {
    pandoc $1 -o pdf/${1%.*}.pdf --toc \
    --template="templates/template.tex" -N \
    -V lang=english -V geometry:hmargin=2.5cm -V geometry:vmargin=2cm \
    -V urlcolor=blue -V linkcolor=black --latex-engine=xelatex
    rm -rf tex2pdf.*
    # explorer "file:///"$(cygpath -w `pwd`)/pdf/${1%.*}.pdf
}

gendoc $1
