docker build -t new_image .
docker tag new_image pbsem2/new_image
docker push pbsem2/new_image

docker network rm rso
docker network create rso
docker run -d --name postgres -e POSTGRES_USER=obauputb -e POSTGRES_PASSWORD=IyELRXpf5tGzTJhhojCSjUYFzXWOETNN -e POSTGRES_DB=obauputb -p 5432:5432 --network rso postgres:13
docker run  -p 8080:8080 --network rso pbsem2/new_image
