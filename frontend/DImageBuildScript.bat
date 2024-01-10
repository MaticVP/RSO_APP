docker build -t frontend_image .
docker tag frontend_image pbsem2/frontend_image
docker push pbsem2/frontend_image
docker run -it -p 3000:3000 pbsem2/frontend_image