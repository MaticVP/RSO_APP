docker build -t frontend_image .
docker tag frontend_image pbsem2/frontend_image
docker push pbsem2/frontend_image
