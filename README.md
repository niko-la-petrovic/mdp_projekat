# MDP Project 2022

## Configuring

### Create docker volumes for Redis & MQ persistence

```pwsh
docker volume create --name mdp_rabbitmq_data --opt type=none --opt device="/home/..."  --opt o=bind
```

```pwsh
docker volume create --name mdp_redis_data --opt type=none --opt device="/home/..."  --opt o=bind
```

### Run docker containers

```bash
docker-compose up -d
```

### Service configuration

Adjust the .properties files in

```bash
MDP_Projekat_22_Web\\resources
MDP_Projekat_22_Web.Clients\\resources
```

### Created/required paths for services

|Property path name|Description|
|---|---|
|persistenceFolderPath|Directory that stores person documents under subfolder named as the person's ID|
|keyStorePath|Path to the keystore needed for the chat server|
|personsLogsFilePath|Person passage logs file (includes every person passing and their basic personal info)|
|wantedPersonsFilePath|JSON file containing an array of wanted person IDs|
|saveFolderPath|Path where the terminals are persisted|
|wantedPersonsDetectedFilePath|Detected wanted persons logs file|

---

## Entry Points

### Redis

 ```http://localhost:8001```

### RabbitMQ

 ```http://localhost:15672```

### Chat Server

```MDP_Projekat_22_Web, mdp.chat.server.Main```

### File Server

```MDP_Projekat_22_Web, mdp.fileserver.Main```

### Notification Server

```MDP_Projekat_22_Web, mdp.register.wanted.Main```

---

### Admin App

```MDP_Projekat_22_Web.Clients, mdp.adminapp.Main```

### Client App

```MDP_Projekat_22_Web.Clients, mdp.clientapp.Main```

### Simulation

```MDP_Projekat_22_Web.Clients, mdp.simulation.Main```
