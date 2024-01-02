set terminal wxt
set terminal png
set encoding utf8
set output "sc1.png"
set xlabel "jours"
set ylabel "infections"
plot"sc1.txt" title 'scenario_1' with linesp lt 1