from confluent_kafka import Consumer, KafkaException
import threading

def consume_messages():
    conf = {
        'bootstrap.servers': '192.168.31.125:9092',
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
    finally:
        consumer.close()

consumer_thread = threading.Thread(target=consume_messages)
consumer_thread.daemon = True
consumer_thread.start()

print("Working detached...")
consumer_thread.join()