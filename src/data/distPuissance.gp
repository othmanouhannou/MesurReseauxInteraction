set terminal wxt
set terminal png
set xlabel 'x'
set ylabel 'p(x)'
set output 'puissance.png'
set logscale xy
set yrange [1e-6:1]
f(x) = lc - gamma * x
fit f(x) 'distribution.txt' using (log($1)):(log($2)) via lc, gamma

c = exp(lc)
power(k) = c * k ** (-gamma)

plot 'distribution.txt' , \
  power(x)


