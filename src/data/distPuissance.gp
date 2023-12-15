set terminal png
set title "Distribution des degrés"
set xlabel 'k'
set ylabel 'p(k)'
set output 'puissance.png'
set logscale xy
set yrange [1e-6:1]
lambda = 6.62208890914917
poisson(k) = lambda ** k * exp(-lambda) / gamma(k + 1)
f(x) = lc - gamma * x
fit f(x) "distribution.txt" using (log($1)):(log($2)) via lc, gamma
c = exp(lc)
power(k) = c * k ** (-gamma)
plot "distribution.txt" title 'Distribution des degrés', \
poisson(x) title 'Loi de poisson', \
power(x) title 'Loi de puissance'

