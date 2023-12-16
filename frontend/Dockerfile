FROM node:16-alpine3.12

WORKDIR /app

ENV PATH /app/node_modules/.bin:$PATH

COPY package.json .

COPY package-lock.json .

RUN npm install
RUN npm install react-router-dom
RUN npm install react-bootstrap bootstrap

COPY . .

EXPOSE 3000

CMD ["npm", "start"]