echo "Undeploying product-api..."
helm uninstall product-api ./product-api/ --namespace my-products --wait

echo "Undeploying product-db..."
helm uninstall product-db ./product-db/ --namespace my-products --wait

echo "Deleting namespace my-products..."
kubectl delete namespace my-products --context minikube
