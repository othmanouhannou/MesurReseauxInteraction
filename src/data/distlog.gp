set terminal wxt
set terminal png
set encoding utf8
set output "distlog.png"
set logscale x 10
set logscale y 10
set xlabel "x"
set ylabel "p(x)"
plot"distribution.txt" with linesp lt 1

