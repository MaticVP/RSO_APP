docker build -t new_image .
docker run  -p 8080:8080 new_image
docker tag new_image pbsem2/new_image
docker push pbsem2/new_image