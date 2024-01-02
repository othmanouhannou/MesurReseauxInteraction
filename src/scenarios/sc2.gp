set terminal wxt
set terminal png
set encoding utf8
set output "sc2.png"
set xlabel "jours"
set ylabel "infections"
set xrange [0:100]
set yrange [0:400000]
plot"sc2.txt" title 'scenario_2' with linesp lt 1