set terminal png 10
set encoding utf8
set output "RandComp.png"
set yrange[0:10000]
set xlabel "jours"
set ylabel "infection"
set title "Comparaison des scénarios aléatoires"
set key left top
set grid
plot"sc1Rand.txt" t "Scenario 1" with linesp lt 1 pt 1,"sc2Rand.txt" t "Scenario 2 "  with linesp lt 2 pt 2,"sc3Rand.txt" t "Scenario 3  " with linesp lt 3 pt 3
