set terminal png 10
set encoding utf8
set output "scBarabasi.png"
set title "Barabasi-Albert"
set yrange[0:10000]
set xlabel "jours"
set ylabel "infection"
plot"sc1Barabasi.txt" t "Scenario 1" with linesp lt 1 pt 1,"sc2Barabasi.txt" t "Scenario 2 "  with linesp lt 2 pt 2,"sc3Barabasi.txt" t "Scenario 3  " with linesp lt 3 pt 3
