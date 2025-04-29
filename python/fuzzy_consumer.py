from confluent_kafka import Consumer, KafkaException
from python.service import ProcessingFuzzyResultService
import threading
import json

def handle_message(message):
    # Parse JSON and turn into dictionary 
    data = json.loads(message.value())
    answers = {item["id"]: item["value"] for item in data}
    
    # Get 9 normalized values
    normalized_9 = ProcessingFuzzyResultService.group_and_normalize(answers)
    print("Result (9):", normalized_9)
    
    # Process values and send
    result = ProcessingFuzzyResultService.process_and_send(normalized_9)
    print("Final result:", result)

def consume_messages():
    conf = {
        'bootstrap.servers': 'localhost:9092',
        'group.id': 'async-group',
        'auto.offset.reset': 'earliest'
    }
    
    consumer = Consumer(conf)
    consumer.subscribe(['kraftt'])

    try:
        while True:
            msg = consumer.poll(0.1)
            if msg is None:
                continue
            if msg.error():
                print(f"Error: {msg.error()}")
                continue
            
            print(f"Received: {msg.value().decode('utf-8')}")
            handle_message(msg)

    finally:
        consumer.close()

consumer_thread = threading.Thread(target=consume_messages)
consumer_thread.daemon = True
consumer_thread.start()

print("Working detached...")
consumer_thread.join()