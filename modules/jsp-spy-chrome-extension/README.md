# JSP Spy Browser Extension

## Chrome release
1. Add `CLIENT_ID`, `CLIENT_SECRET`, and `REFRESH_TOKEN` as environment variables
2. `npm release:cws`

## Development

1. `yarn install`
2. `yarn dev [vendor]` where `[vendor]` can be `chrome`, `firefox`, `opera` or `edge`
3. Temporary load your extension to your browser from `dist/vendor`   
See [webextension-toolbox/webextension-toolbox](https://github.com/webextension-toolbox/webextension-toolbox) for more information

## Release

### Chrome
1. Add `CLIENT_ID`, `CLIENT_SECRET`, and `REFRESH_TOKEN` as environment variables to your user/system
2. `yarn release:cws`