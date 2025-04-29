import json
import logging
from confluent_kafka import Producer
from datetime import datetime
from typing import Dict, Any

logging.basicConfig(level=logging.INFO)
logger = logging.getLogger(__name__)

class KafkaResultProducer:
    def __init__(self, bootstrap_servers: str = 'localhost:9092'):

        self.conf = {
            'bootstrap.servers': bootstrap_servers,
            'acks': 'all',
            'retries': 3,
            'message.timeout.ms': 30000,
            'partitioner': 'consistent'
        }
        
        self.producer = Producer(self.conf)
        logger.info(f"Producer initialized for servers: {bootstrap_servers}")

    def send_processed_data(self, topic: str, data: Dict[str, Any]):
        try:
            enriched_data = {
                **data,
                'processed_at': datetime.utcnow().isoformat(),
                'version': '1.0'
            }

            serialized_data = json.dumps(enriched_data).encode('utf-8')
            
            self.producer.produce(
                topic=topic,
                value=serialized_data,
                on_delivery=self._delivery_callback
            )
            
            self.producer.flush()
            
        except Exception as e:
            logger.error(f"Kafka producer error: {e}")
            raise

    def _delivery_callback(self, err, msg):
        if err:
            print(f'Delivery failed: {err}')
        else:
            print(f'Message delivered to {msg.topic()} [{msg.partition()}]')

    def close(self):
        self.producer.close()
        logger.info("Producer connection closed")