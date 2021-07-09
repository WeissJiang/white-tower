import Document from 'next/document'
import { ServerStyleSheet } from 'styled-components'

export default class WhiteTowerDocument extends Document {

    static async getInitialProps(ctx) {
        const sheet = new ServerStyleSheet()
        const _renderPage = ctx.renderPage

        try {
            ctx.renderPage = () => {
                return _renderPage({
                    enhanceApp: (App) => (props) =>
                        sheet.collectStyles(<App {...props} />),
                })
            }

            const initialProps = await Document.getInitialProps(ctx)
            return {
                ...initialProps,
                styles: (
                    <>
                        {initialProps.styles}
                        {sheet.getStyleElement()}
                    </>
                ),
            }
        } finally {
            sheet.seal()
        }
    }
}