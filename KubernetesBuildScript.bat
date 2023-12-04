kubectl create -f test.yaml
kubectl apply  -f test.yaml
kubectl port-forward svc/demo 8080:8080