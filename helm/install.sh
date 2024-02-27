echo "Deploying product-db..."
helm install product-db/ --generate-name --namespace my-products --create-namespace --wait

echo "Deploying product-api..."
helm install product-api/ --generate-name --namespace my-products --create-namespace --wait

# helm install product-db ./product-db/ --namespace my-products --create-namespace
# helm install product-api ./product-api/ --namespace my-products --create-namespace
