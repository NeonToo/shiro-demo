const path = require('path');
const rootPath = path.resolve(__dirname, './');

module.exports = function(env) {
    console.log(`The current environment is ${env}`);

    return require(`${rootPath}/build/webpack.${env}.js`);
};