# BUILD STAGE
FROM node as node

WORKDIR /issuemanagement

COPY . .


RUN npm install
RUN npm run build

# DEPLOYEMENT STAGE
FROM nginx
COPY --from=node /issuemanagement/dist/n11-finalproject-frontend /usr/share/nginx/html

