from confluent_kafka import Consumer, KafkaException
import threading

def consume_messages():
    conf = {
        'bootstrap.servers': '192.168.31.125:9092',
        'group.id': 'async-group',
        'auto.offset.reset': 'earliest'
    }
    
    consumer = Consumer(conf)
    consumer.subscribe(['kraft-topic'])

    try:
        while True:
            msg = consumer.poll(0.1)
            if msg is None:
                continue
            if msg.error():
                print(f"Ошибка: {msg.error()}")
                continue
            
            print(f"Асинхронно получено: {msg.value().decode('utf-8')}")
    finally:
        consumer.close()

# Запуск в отдельном потоке
consumer_thread = threading.Thread(target=consume_messages)
consumer_thread.daemon = True
consumer_thread.start()

# Основной поток может выполнять другие задачи
print("Working detached...")
consumer_thread.join()  # Ожидание завершения