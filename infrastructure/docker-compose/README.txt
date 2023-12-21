Step 1: RUN Zookeeper
    - docker-compose -f common.yml -f zookeeper.yml up

Step 2: Check health by "ruok"
    - echo ruok | nc localhost 2181

Step 3: RUN Kafka
    - docker-compose -f common.yml -f kafka_cluster.yml up

Step 4: RUN Kafka initialization
    - docker-compose -f common.yml -f init_kafka.yml up