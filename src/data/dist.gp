set terminal wxt
set terminal png
set encoding utf8
set output "distribution.png"
set xlabel "x"
set ylabel "p(x)"
plot"distribution.txt" with linesp lt 1

