echo "Creating namespace my-products..."
kubectl create namespace my-products --context minikube

echo "Deploying product-db..."
kubectl -n my-products apply -f ./product-db.yaml --context minikube --wait=true
while ! kubectl get pods -n my-products --context minikube | grep -m1 -E 'product-db-.*Running.*' ; do
    echo "Waiting for product-db to start..."
    sleep 1
done

echo "Deploying product-api..."
kubectl -n my-products apply -f ./product-api.yaml --context minikube --wait=true
while ! kubectl get pods -n my-products --context minikube | grep -m1 -E 'product-api-.*1/1.*Running.*' ; do
    echo "Waiting for product-api to start..."
    sleep 1
done

echo "Add curl to the product-api pods..."
for POD in $(kubectl get pods -n my-products --context minikube | grep -E '^product-api-' | cut -d' ' -f1) ; do
    kubectl -n my-products exec -it ${POD} --context minikube -- apk --no-cache add curl
done


echo "Deploying my-products-ingress..."
kubectl -n my-products apply -f ./my-products-ingress.yaml --context minikube --wait=true
while ! kubectl get ingress -n my-products --context minikube | grep -m1 -E '^my-products-ingress' ; do
    echo "Waiting for my-products-ingress to start..."
    sleep 1
done

echo "Done deploying my-products to minikube"
echo "You can access the Swagger UI at http://$(minikube ip)/swagger-ui.html"
echo "You can access the application at http://$(minikube ip)/products"
echo "You can access health actuators at http://$(minikube ip)/actuator/health"
