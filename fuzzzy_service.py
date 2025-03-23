import numpy as np
import skfuzzy as fuzz
import matplotlib.pyplot as plt

# Universe of discourse
# Range 0..1 with step 0.1
universe = np.arange(0, 1, 0.01)

# Test values (Hysteria)
hysteria_low = fuzz.trapmf(universe, [-0.1, 0, 0.12, 0.23])
hysteria_mid = fuzz.trapmf(universe, [0.12, 0.23, 0.35, 0.43])
hysteria_elevated = fuzz.trapmf(universe, [0.35, 0.43, 0.5, 0.57])
hysteria_high = fuzz.trapmf(universe, [0.5, 0.57, 0.7, 0.75])
hysteria_very_high = fuzz.trapmf(universe, [0.7, 0.75, 1, 1.1])

# Test values (Hypochondria)
hypochondria_low = fuzz.trapmf(universe, [-0.1, 0, 0.14, 0.25])
hypochondria_mid = fuzz.trapmf(universe, [0.14, 0.25, 0.37, 0.44])
hypochondria_elevated = fuzz.trapmf(universe, [0.37, 0.44, 0.53, 0.6])
hypochondria_high = fuzz.trapmf(universe, [0.53, 0.6, 0.73, 0.79])
hypochondria_very_high = fuzz.trapmf(universe, [0.73, 0.79, 1, 1.1])

# Test values (Depression)
depression_low = fuzz.trapmf(universe, [-0.1, 0, 0.12, 0.24])
depression_mid = fuzz.trapmf(universe, [0.12, 0.24, 0.38, 0.47])
depression_elevated = fuzz.trapmf(universe, [0.38, 0.47, 0.59, 0.66])
depression_high = fuzz.trapmf(universe, [0.59, 0.66, 0.74, 0.8])
depression_very_high = fuzz.trapmf(universe, [0.74, 0.8, 1, 1.1])

# Test values (Psychopathy)
psychopathy_low = fuzz.trapmf(universe, [-0.1, 0, 0.16, 0.26])
psychopathy_mid = fuzz.trapmf(universe, [0.16, 0.26, 0.41, 0.53])
psychopathy_elevated = fuzz.trapmf(universe, [0.41, 0.53, 0.58, 0.62])
psychopathy_high = fuzz.trapmf(universe, [0.58, 0.62, 0.74, 0.81])
psychopathy_very_high = fuzz.trapmf(universe, [0.74, 0.81, 1, 1.1])

# Test values (Paranoia)
paranoia_low = fuzz.trapmf(universe, [-0.1, 0, 0.15, 0.26])
paranoia_mid = fuzz.trapmf(universe, [0.15, 0.26, 0.39, 0.49])
paranoia_elevated = fuzz.trapmf(universe, [0.39, 0.49, 0.61, 0.67])
paranoia_high = fuzz.trapmf(universe, [0.61, 0.67, 0.79, 0.85])
paranoia_very_high = fuzz.trapmf(universe, [0.79, 0.85, 1, 1.1])

# Test values (Psychasthenia)
psychasthenia_low = fuzz.trapmf(universe, [-0.1, 0, 0.18, 0.29])
psychasthenia_mid = fuzz.trapmf(universe, [0.18, 0.29, 0.41, 0.51])
psychasthenia_elevated = fuzz.trapmf(universe, [0.41, 0.51, 0.64, 0.68])
psychasthenia_high = fuzz.trapmf(universe, [0.64, 0.68, 0.8, 0.86])
psychasthenia_very_high = fuzz.trapmf(universe, [0.8, 0.86, 1, 1.1])

# Test values (Schizophrenia)
schizophrenia_low = fuzz.trapmf(universe, [-0.1, 0, 0.17, 0.28])
schizophrenia_mid = fuzz.trapmf(universe, [0.17, 0.28, 0.44, 0.55])
schizophrenia_elevated = fuzz.trapmf(universe, [0.44, 0.55, 0.67, 0.7])
schizophrenia_high = fuzz.trapmf(universe, [0.67, 0.7, 0.81, 0.86])
schizophrenia_very_high = fuzz.trapmf(universe, [0.81, 0.86, 1, 1.1])

# Test values (Hypomania)
hypomania_low = fuzz.trapmf(universe, [-0.1, 0, 0.16, 0.27])
hypomania_mid = fuzz.trapmf(universe, [0.16, 0.27, 0.45, 0.56])
hypomania_elevated = fuzz.trapmf(universe, [0.45, 0.56, 0.68, 0.71])
hypomania_high = fuzz.trapmf(universe, [0.68, 0.71, 0.82, 0.87])
hypomania_very_high = fuzz.trapmf(universe, [0.82, 0.87, 1, 1.1])

# Test values (Introversion)
introversion_low = fuzz.trapmf(universe, [-0.1, 0, 0.16, 0.29])
introversion_mid = fuzz.trapmf(universe, [0.16, 0.29, 0.48, 0.58])
introversion_elevated = fuzz.trapmf(universe, [0.48, 0.58, 0.67, 0.72])
introversion_high = fuzz.trapmf(universe, [0.67, 0.72, 0.84, 0.89])
introversion_very_high = fuzz.trapmf(universe, [0.84, 0.89, 1, 1.1])

# Graphics
plt.figure()

plt.plot(universe, hysteria_low, label = "hy_low", color = 'salmon')
plt.plot(universe, hysteria_mid, label = "hy_mid", color = 'salmon')
plt.plot(universe, hysteria_elevated, label = "hy_elevated", color = 'salmon')
plt.plot(universe, hysteria_high, label = "hy_high", color = 'salmon')
plt.plot(universe, hysteria_very_high, label = "hy_very_high", color = 'salmon')

plt.plot(universe, hypochondria_low, label = "hs_low", color = 'limegreen')
plt.plot(universe, hypochondria_mid, label = "hs_mid", color = 'limegreen')
plt.plot(universe, hypochondria_elevated, label = "hs_elevated", color = 'limegreen')
plt.plot(universe, hypochondria_high, label = "hs_high", color = 'limegreen')
plt.plot(universe, hypochondria_very_high, label = "hs_very_high", color = 'limegreen')

plt.title('Membership Functions')
plt.ylabel('Degree of Membership')
plt.xlabel('Universe of Discourse') 
plt.legend(loc = 'upper right')
plt.grid(True)
plt.show()