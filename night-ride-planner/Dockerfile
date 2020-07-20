FROM node:5.0.0

WORKDIR /usr/src/night-ride-planner/
COPY . ./

WORKDIR /usr/src/night-ride-planner/

RUN npm install --loglevel verbose

EXPOSE 4200

CMD ["npm", "start"]
