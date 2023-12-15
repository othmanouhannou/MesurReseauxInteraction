set terminal wxt
set terminal png
set encoding utf8
set output "distribution.png"
set xlabel "x"
set ylabel "p(x)"
set title "Distribution des degrés"
plot"distribution.txt" title 'Distribution des degrés' with linesp lt 1

