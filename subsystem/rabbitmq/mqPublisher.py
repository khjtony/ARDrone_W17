#!/usr/bin/env python
import pika
import sys
import time
connection = pika.BlockingConnection(pika.ConnectionParameters(
        host='localhost'))
channel = connection.channel()

channel.exchange_declare(exchange='logs',
                         type='fanout')

message = ' '.join(sys.argv[1:]) or "info: Hello World!"

while True:
    channel.basic_publish(exchange='logs',
                          routing_key='',
                          body=message+str(time.clock()))
    print(" [x] Sent %r" % (message+str(time.clock())))
    time.sleep(1)
connection.close()