SHELL := bash # ;(

FILE = evaluations
PDFGEN = pdflatex
BIBTEX = bibtex
CROP = pdfcrop
EDITOR = gvim

all: pdf

pdf: $(FILE).tex
	touch $(FILE).aux; $(BIBTEX) $(FILE).aux;	$(PDFGEN) $(FILE).tex  

crop: $(FILE).pdf
	$(CROP) $(FILE).pdf

cropshow: crop
	  acroread $(FILE)-crop.pdf 1>/dev/null 2>&1

print: cropshow

show: okularshow

edit: $(FILE).tex
	$(EDITOR) $(FILE).tex

acroshow: $(FILE).pdf
	acroread $(FILE).pdf 1>/dev/null 2>&1 &

okularshow: $(FILE).pdf
	okular $(FILE).pdf 1>/dev/null 2>&1  &

lowres: $(FILE).pdf 
	gs -sDEVICE=pdfwrite -dPDFSETTINGS=/printer -dCompatibilityLevel=1.4 -dNOPAUSE -dQUIET -dBATCH -sOutputFile=$(FILE)-lowres.pdf $(FILE).pdf

clean:
	rm -fr *.aux *._aux *.log *.dvi *.toc *.bbl *.blg *.out *.eps *.ps *.tex~ *.bak *.brf *.bbl *.log *.sh~ *-crop.pdf *.synctex.gz *pdf.ps *.vtc *.bib~ *.cls~ *.backup Makefile~ *.log *.bbl *.ilg *.log *.idx *.ps

.PHONY: clean
