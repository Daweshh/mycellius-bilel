import React, { useMemo } from "react";
import { marked } from "marked";
import DOMPurify from "dompurify";

export default function SafeMarkdown({ markdown }) {
  const html = useMemo(() => {
    const raw = typeof markdown === "string" ? markdown : "";
    const rendered = marked.parse(raw ?? "");
    return DOMPurify.sanitize(rendered);
  }, [markdown]);

  return <div dangerouslySetInnerHTML={{ __html: html }} />;
}

