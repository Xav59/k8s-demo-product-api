kubectl -n my-products delete -f ./my-products-ingress.yaml --context minikube --wait=true
kubectl -n my-products delete -f ./product-db.yaml --context minikube --wait=true
kubectl -n my-products delete -f ./product-api.yaml --context minikube --wait=true
kubectl delete namespace my-products --context minikube
