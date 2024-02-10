// components/ChatMessage.tsx
import ReactMarkdown from 'react-markdown';
import rehypeHighlight from 'rehype-highlight';
import 'highlight.js/styles/atom-one-light.css';

export default function ChatMessage({ content, role }: Message) {
  return (
    <div className="w-full mb-4">
      <div className="flex flex-col md:flex-row md:gap-2">
        <div className="text-2xl">{role === 'bot' ? '🤖' : '🧑‍💻'}</div>
        <div className="max-w-full overflow-x-scroll">
          <ReactMarkdown rehypePlugins={[[rehypeHighlight, { ignoreMissing: true }]]}>{content || ''}</ReactMarkdown>
        </div>
      </div>
    </div>
  );
}
