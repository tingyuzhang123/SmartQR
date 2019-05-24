'use strict';

const AWS = require('aws-sdk');
const shortid = require('shortid');
shortid.characters('0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ$@');
const dynamoDb = new AWS.DynamoDB.DocumentClient();

module.exports.create = (event, context, callback) => {
  const timestamp = new Date().getTime();
  const data = JSON.parse(event.body);  
  const item = Object.assign(data, {id: shortid.generate(),createdAt: timestamp,updatedAt: timestamp})

  const params = {
    TableName: 'dev-smartqr-articles',
    Item: item,
  };

  //Write the article to the database
  dynamoDb.put(params, (error) => {
    //Handle potential errors
    if (error) {
      console.error(error);
      callback(null, {
        statusCode: error.statusCode || 501,
        headers: { 'Content-Type': 'text/plain' },
        body: 'Couldn\'t create the item.',
      });
      return;
    }
    //Create a response
    const response = {
      statusCode: 200,
	  //Set cross origin which allowed the chrome extension call
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET, POST, PATCH, PUT, DELETE, OPTIONS'
      }, 
      body: JSON.stringify(params.Item),
    };
    callback(null, response);
  });
};
