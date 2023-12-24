set terminal wxt
set terminal png
set encoding utf8
set output "distributionDistance.png"
set xlabel "d"
set ylabel "p(d)"
set key on inside center top
plot"distribution1.txt" with linesp lt 1 pt 1
