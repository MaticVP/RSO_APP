kubectl create deployment test --image=pbsem2/new_image --dry-run -o=yaml > test.yaml
kubectl apply -f test.yaml
kubectl port-forward svc/demo 8080:8080