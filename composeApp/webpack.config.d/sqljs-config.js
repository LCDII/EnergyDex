config.resolve = {
    fallback: {
        fs: false,
        path: false,
        crypto: false,
    }
};

const CopyWebpackPlugin = require('copy-webpack-plugin');
const path = require('path');

config.plugins.push(
    new CopyWebpackPlugin({
        patterns: [
            '../../node_modules/sql.js/dist/sql-wasm.wasm',
            path.resolve(__dirname, '../../../../composeApp/src/wasmJsMain/resources/index.html'),
            path.resolve(__dirname, '../../../../composeApp/src/wasmJsMain/resources/styles.css')
        ]
    })
);
