"use strict";
var request = require("request");

const AWS = require("aws-sdk"); // eslint-disable-line import/no-extraneous-dependencies

const dynamoDb = new AWS.DynamoDB.DocumentClient();
const params = {
  TableName: "dev-smartqr-articles"
};

module.exports.list = (event, context, callback) => {
  // fetch all todos from the database
  dynamoDb.scan(params, (error, result) => {
    // handle potential errors
    if (error) {
      console.error(error);
      callback(null, {
        statusCode: error.statusCode || 501,
        headers: { "Content-Type": "text/plain" },
        body: "Couldn't fetch the todos."
      });
      return;
    }


    let test = {
      json: {
          sampleList: result.Items.map(x => {
            var a = {id:x.id, title:x.title, url:x.url, image:x.image, weight: 0};
            return a;
          }),
          src: event.pathParameters.title            
        }
    }

    console.log(JSON.stringify(test));


    var res = "";
    request.post(
      "http://default-environment.7dazupxx86.ap-southeast-2.elasticbeanstalk.com/infos",
        {
        json: {
          sampleList: result.Items.map(x => {
            var a = {id:x.id, title:x.title, url:x.url, image:x.image, weight: 0};
            return a;
          }),
          src: decodeURI(event.pathParameters.title)
        }
      },
      function(error, response, b) {
        if (!error && response.statusCode == 200) {
          console.log(b);
          const response1 = {
            statusCode: 200,
            headers: {
              "Access-Control-Allow-Origin": "*"
            },
            body: JSON.stringify(b)
          };
          callback(null, response1);
        }
      }
    );

    // const res = {
    //   rentals: result.Items
    // };

    // create a response
  });
};
