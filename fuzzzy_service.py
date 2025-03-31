import numpy as np
import skfuzzy as fuzz
import matplotlib.pyplot as plt

# Input data (order: Hysteria, Hypochondria, Depression, Psychopathy, 
# Paranoia, Psychasthenia, Schizophrenia, Hypomania, Introversion)
data = [0.96, 0.34, 0.45, 0.10, 0.45, 0.69, 0.34, 0.23, 0.78]

# Universe of discourse (range 0..1 with step 0.01)
universe = np.arange(0, 1, 0.01)

# Dictionary containing all membership functions (trapezoidal)
membership_functions = {
    # Hysteria membership functions
    'Hysteria': {
        'low': fuzz.trapmf(universe, [-0.1, 0, 0.12, 0.23]),
        'mid': fuzz.trapmf(universe, [0.12, 0.23, 0.35, 0.43]),
        'elevated': fuzz.trapmf(universe, [0.35, 0.43, 0.5, 0.57]),
        'high': fuzz.trapmf(universe, [0.5, 0.57, 0.7, 0.75]),
        'very_high': fuzz.trapmf(universe, [0.7, 0.75, 1, 1.1])
    },
    
    # Hypochondria membership functions
    'Hypochondria': {
        'low': fuzz.trapmf(universe, [-0.1, 0, 0.14, 0.25]),
        'mid': fuzz.trapmf(universe, [0.14, 0.25, 0.37, 0.44]),
        'elevated': fuzz.trapmf(universe, [0.37, 0.44, 0.53, 0.6]),
        'high': fuzz.trapmf(universe, [0.53, 0.6, 0.73, 0.79]),
        'very_high': fuzz.trapmf(universe, [0.73, 0.79, 1, 1.1])
    },
    
    # Depression membership functions
    'Depression': {
        'low': fuzz.trapmf(universe, [-0.1, 0, 0.12, 0.24]),
        'mid': fuzz.trapmf(universe, [0.12, 0.24, 0.38, 0.47]),
        'elevated': fuzz.trapmf(universe, [0.38, 0.47, 0.59, 0.66]),
        'high': fuzz.trapmf(universe, [0.59, 0.66, 0.74, 0.8]),
        'very_high': fuzz.trapmf(universe, [0.74, 0.8, 1, 1.1])
    },
    
    # Psychopathy membership functions
    'Psychopathy': {
        'low': fuzz.trapmf(universe, [-0.1, 0, 0.16, 0.26]),
        'mid': fuzz.trapmf(universe, [0.16, 0.26, 0.41, 0.53]),
        'elevated': fuzz.trapmf(universe, [0.41, 0.53, 0.58, 0.62]),
        'high': fuzz.trapmf(universe, [0.58, 0.62, 0.74, 0.81]),
        'very_high': fuzz.trapmf(universe, [0.74, 0.81, 1, 1.1])
    },
    
    # Paranoia membership functions
    'Paranoia': {
        'low': fuzz.trapmf(universe, [-0.1, 0, 0.15, 0.26]),
        'mid': fuzz.trapmf(universe, [0.15, 0.26, 0.39, 0.49]),
        'elevated': fuzz.trapmf(universe, [0.39, 0.49, 0.61, 0.67]),
        'high': fuzz.trapmf(universe, [0.61, 0.67, 0.79, 0.85]),
        'very_high': fuzz.trapmf(universe, [0.79, 0.85, 1, 1.1])
    },
    
    # Psychasthenia membership functions
    'Psychasthenia': {
        'low': fuzz.trapmf(universe, [-0.1, 0, 0.18, 0.29]),
        'mid': fuzz.trapmf(universe, [0.18, 0.29, 0.41, 0.51]),
        'elevated': fuzz.trapmf(universe, [0.41, 0.51, 0.64, 0.68]),
        'high': fuzz.trapmf(universe, [0.64, 0.68, 0.8, 0.86]),
        'very_high': fuzz.trapmf(universe, [0.8, 0.86, 1, 1.1])
    },
    
    # Schizophrenia membership functions
    'Schizophrenia': {
        'low': fuzz.trapmf(universe, [-0.1, 0, 0.17, 0.28]),
        'mid': fuzz.trapmf(universe, [0.17, 0.28, 0.44, 0.55]),
        'elevated': fuzz.trapmf(universe, [0.44, 0.55, 0.67, 0.7]),
        'high': fuzz.trapmf(universe, [0.67, 0.7, 0.81, 0.86]),
        'very_high': fuzz.trapmf(universe, [0.81, 0.86, 1, 1.1])
    },
    
    # Hypomania membership functions
    'Hypomania': {
        'low': fuzz.trapmf(universe, [-0.1, 0, 0.16, 0.27]),
        'mid': fuzz.trapmf(universe, [0.16, 0.27, 0.45, 0.56]),
        'elevated': fuzz.trapmf(universe, [0.45, 0.56, 0.68, 0.71]),
        'high': fuzz.trapmf(universe, [0.68, 0.71, 0.82, 0.87]),
        'very_high': fuzz.trapmf(universe, [0.82, 0.87, 1, 1.1])
    },
    
    # Introversion membership functions
    'Introversion': {
        'low': fuzz.trapmf(universe, [-0.1, 0, 0.16, 0.29]),
        'mid': fuzz.trapmf(universe, [0.16, 0.29, 0.48, 0.58]),
        'elevated': fuzz.trapmf(universe, [0.48, 0.58, 0.67, 0.72]),
        'high': fuzz.trapmf(universe, [0.67, 0.72, 0.84, 0.89]),
        'very_high': fuzz.trapmf(universe, [0.84, 0.89, 1, 1.1])
    }
}

# Process each data point through corresponding membership functions
results = {}
categories_order = [
    'Hysteria', 'Hypochondria', 'Depression', 'Psychopathy',
    'Paranoia', 'Psychasthenia', 'Schizophrenia', 'Hypomania', 'Introversion'
]

for i, category in enumerate(categories_order):
    value = data[i]
    results[category] = {}
    
    # Calculate membership for each function in this category
    for mf_name, mf in membership_functions[category].items():
        membership = fuzz.interp_membership(universe, mf, value)
        results[category][mf_name] = membership

# Display results for each category
for category, mfs in results.items():
    print(f"\n{category}:")
    for mf_name, membership in mfs.items():
        print(f"  {mf_name}: {membership:.2f}")

# Create a figure with subplots for each category
plt.figure(figsize=(15, 20))
plt.suptitle('Membership Functions for Psychological Assessment', y=1.02, fontsize=16)

# Create 9 subplots (3 rows x 3 columns)
for i, category in enumerate(categories_order):
    plt.subplot(3, 3, i+1)
    
    # Plot all membership functions for this category
    for mf_name, mf in membership_functions[category].items():
        plt.plot(universe, mf, label=mf_name)
    
    # Mark the input data point
    plt.scatter([data[i]], [0], color='black', s=100, zorder=5, 
               label=f'Input value: {data[i]:.2f}')
    
    # Draw a vertical line at the input value
    plt.axvline(x=data[i], color='gray', linestyle='--')
    
    # Calculate and display membership values at this point
    membership_values = results[category]
    for j, (mf_name, mf) in enumerate(membership_functions[category].items()):
        y_pos = 0.9 - j*0.1
        plt.text(data[i]+0.02, y_pos, 
                f"{mf_name}: {membership_values[mf_name]:.2f}",
                fontsize=9, bbox=dict(facecolor='white', alpha=0.8))
    
    # Customize the subplot
    plt.title(category)
    plt.xlabel('Value')
    plt.ylabel('Membership Degree')
    plt.grid(True)
    plt.ylim(-0.05, 1.05)
    plt.legend(loc='upper right', fontsize=8)

plt.tight_layout()
plt.show()