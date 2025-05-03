from fuzzy_producer import KafkaResultProducer
import numpy as np
import skfuzzy as fuzz

class ProcessingFuzzyResultService:
    @staticmethod
    def process_answers(data: list[float]):
        # Universe of discourse (range 0..1 with step 0.01)
        universe = np.arange(0, 1, 0.01)

        # Dictionary containing all membership functions (trapezoidal)
        membership_functions = {
            # Hysteria membership functions
            'hysteria': {
                'low': fuzz.trapmf(universe, [-0.1, 0, 0.12, 0.23]),
                'mid': fuzz.trapmf(universe, [0.12, 0.23, 0.35, 0.43]),
                'elevated': fuzz.trapmf(universe, [0.35, 0.43, 0.5, 0.57]),
                'high': fuzz.trapmf(universe, [0.5, 0.57, 0.7, 0.75]),
                'very_high': fuzz.trapmf(universe, [0.7, 0.75, 1, 1.1])
            },
            
            # Hypochondria membership functions
            'hypochondria': {
                'low': fuzz.trapmf(universe, [-0.1, 0, 0.14, 0.25]),
                'mid': fuzz.trapmf(universe, [0.14, 0.25, 0.37, 0.44]),
                'elevated': fuzz.trapmf(universe, [0.37, 0.44, 0.53, 0.6]),
                'high': fuzz.trapmf(universe, [0.53, 0.6, 0.73, 0.79]),
                'very_high': fuzz.trapmf(universe, [0.73, 0.79, 1, 1.1])
            },
            
            # Depression membership functions
            'depression': {
                'low': fuzz.trapmf(universe, [-0.1, 0, 0.12, 0.24]),
                'mid': fuzz.trapmf(universe, [0.12, 0.24, 0.38, 0.47]),
                'elevated': fuzz.trapmf(universe, [0.38, 0.47, 0.59, 0.66]),
                'high': fuzz.trapmf(universe, [0.59, 0.66, 0.74, 0.8]),
                'very_high': fuzz.trapmf(universe, [0.74, 0.8, 1, 1.1])
            },
            
            # Psychopathy membership functions
            'psychopathy': {
                'low': fuzz.trapmf(universe, [-0.1, 0, 0.16, 0.26]),
                'mid': fuzz.trapmf(universe, [0.16, 0.26, 0.41, 0.53]),
                'elevated': fuzz.trapmf(universe, [0.41, 0.53, 0.58, 0.62]),
                'high': fuzz.trapmf(universe, [0.58, 0.62, 0.74, 0.81]),
                'very_high': fuzz.trapmf(universe, [0.74, 0.81, 1, 1.1])
            },
            
            # Paranoia membership functions
            'paranoia': {
                'low': fuzz.trapmf(universe, [-0.1, 0, 0.15, 0.26]),
                'mid': fuzz.trapmf(universe, [0.15, 0.26, 0.39, 0.49]),
                'elevated': fuzz.trapmf(universe, [0.39, 0.49, 0.61, 0.67]),
                'high': fuzz.trapmf(universe, [0.61, 0.67, 0.79, 0.85]),
                'very_high': fuzz.trapmf(universe, [0.79, 0.85, 1, 1.1])
            },
            
            # Psychasthenia membership functions
            'psychasthenia': {
                'low': fuzz.trapmf(universe, [-0.1, 0, 0.18, 0.29]),
                'mid': fuzz.trapmf(universe, [0.18, 0.29, 0.41, 0.51]),
                'elevated': fuzz.trapmf(universe, [0.41, 0.51, 0.64, 0.68]),
                'high': fuzz.trapmf(universe, [0.64, 0.68, 0.8, 0.86]),
                'very_high': fuzz.trapmf(universe, [0.8, 0.86, 1, 1.1])
            },
            
            # Schizophrenia membership functions
            'schizophrenia': {
                'low': fuzz.trapmf(universe, [-0.1, 0, 0.17, 0.28]),
                'mid': fuzz.trapmf(universe, [0.17, 0.28, 0.44, 0.55]),
                'elevated': fuzz.trapmf(universe, [0.44, 0.55, 0.67, 0.7]),
                'high': fuzz.trapmf(universe, [0.67, 0.7, 0.81, 0.86]),
                'very_high': fuzz.trapmf(universe, [0.81, 0.86, 1, 1.1])
            },
            
            # Hypomania membership functions
            'hypomania': {
                'low': fuzz.trapmf(universe, [-0.1, 0, 0.16, 0.27]),
                'mid': fuzz.trapmf(universe, [0.16, 0.27, 0.45, 0.56]),
                'elevated': fuzz.trapmf(universe, [0.45, 0.56, 0.68, 0.71]),
                'high': fuzz.trapmf(universe, [0.68, 0.71, 0.82, 0.87]),
                'very_high': fuzz.trapmf(universe, [0.82, 0.87, 1, 1.1])
            },
            
            # Introversion membership functions
            'introversion': {
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
            'hysteria', 'hypochondria', 'depression', 'psychopathy',
            'paranoia', 'psychasthenia', 'schizophrenia', 'hypomania', 'introversion'
        ]

        for i, category in enumerate(categories_order):
            value = data[i]
            results[category] = {}
            
            # Calculate membership for each function in this category
            for mf_name, mf in membership_functions[category].items():
                membership = fuzz.interp_membership(universe, mf, value)
                results[category][mf_name] = membership

        return results

    @staticmethod
    def process_and_send(data: list[float]):
        producer = KafkaResultProducer()

        results = ProcessingFuzzyResultService.process_answers(data)
        producer.send_processed_data(
            topic='results',
            data=results
        )



    @staticmethod
    def group_and_normalize(answers):
        # Normalize all values into range [0, 1]
        normalized = {k: (v - 1) / 4 for k, v in answers.items()}
        
        # TODO change grouping method
        # Group all question into 9 categories
        groups = []
        for i in range(1, 10):  # 9 categories
            start = (i - 1) * 8 + 1  # q1, q9, q17, ..., q57
            end = start + 8 if i < 9 else 72  # q8, q16, ..., q71
            group_keys = [f"q{j}" for j in range(start, end)]
            group_values = [normalized.get(k, 0.0) for k in group_keys]
            groups.append(sum(group_values) / len(group_values))  # Average
        
        return groups